package com.thenocturn.pos.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thenocturn.pos.dto.OrderItemRequest;
import com.thenocturn.pos.dto.OrderItemResponse;
import com.thenocturn.pos.dto.OrderRequest;
import com.thenocturn.pos.dto.OrderResponse;
import com.thenocturn.pos.entity.Order;
import com.thenocturn.pos.entity.OrderItem;
import com.thenocturn.pos.entity.OrderStatus;
import com.thenocturn.pos.entity.PaymentStatus;
import com.thenocturn.pos.entity.Product;
import com.thenocturn.pos.repository.OrderRepository;
import com.thenocturn.pos.repository.ProductRepository;
import com.thenocturn.pos.service.EmailService;
import com.thenocturn.pos.service.InventoryService;
import com.thenocturn.pos.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;


import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    private final EmailService emailService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,InventoryService inventoryService, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.inventoryService = inventoryService;
        this.emailService = emailService;


    }

    @Override
    @Transactional

    public OrderResponse  createOrder(OrderRequest request) {

        // STEP 1: Create Order
        Order order = new Order();
        String orderNumber = "ORD-" + System.currentTimeMillis();
        order.setOrderNumber(orderNumber);
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setDiscount(request.getDiscount());
        order.setTax(request.getTax());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal subtotal = BigDecimal.ZERO;

        // STEP 2: Process each item
        for (OrderItemRequest itemReq : request.getItems()) {

            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // check stock
            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }

            // calculate item total
            BigDecimal unitPrice = product.getPrice();
            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            // create OrderItem
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(unitPrice);
            item.setTotalPrice(totalPrice);
            item.setOrder(order);

            orderItems.add(item);

            // reduce stock (IMPORTANT)
//            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
//            productRepository.save(product);
            
            inventoryService.removeStock(
                    product.getId(),
                    itemReq.getQuantity(),
                    "ORDER"
            );
            
            subtotal = subtotal.add(totalPrice);
        }

        // STEP 3: Set order totals
        order.setSubtotal(subtotal);

        BigDecimal discount = request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO;
        BigDecimal tax = request.getTax() != null ? request.getTax() : BigDecimal.ZERO;

        BigDecimal total = subtotal.subtract(discount).add(tax);

        order.setTotalAmount(total);

        // STEP 4: attach items
        order.setItems(orderItems);
        
        Order savedOrder = orderRepository.save(order);
        
        OrderResponse response = OrderResponse.builder()
                .id(savedOrder.getId())
                .orderNumber(savedOrder.getOrderNumber())
                .customerName(savedOrder.getCustomerName())
                .totalAmount(savedOrder.getTotalAmount())
                .items(savedOrder.getItems().stream().map(item ->
                        OrderItemResponse.builder()
                                .productName(item.getProduct().getName())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .build()
                ).toList())
                .build();
        
        
     // SEND EMAIL (ASYNC)
        emailService.sendOrderEmailWithInvoice(
                request.getCustomerEmail(),
                response
        );

        // STEP 5: save order (cascade saves items)
        return response;
    }
    
    
    
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        

        // ❌ prevent double cancel
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }

        // ❌ prevent cancel after completion
        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Completed order cannot be cancelled");
        }

        // STEP 1: restore stock for each item
        for (OrderItem item : order.getItems()) {

            Product product = item.getProduct();

            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);

            // STEP 2: inventory log (IN)
            inventoryService.addStock(
                    product.getId(),
                    item.getQuantity(),
                    "ORDER_CANCEL"
            );
        }

        // STEP 3: update order status
        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }
    
    @Override
    public void markOrderAsPaid(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Only CREATED orders can be paid");
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.PAID);

        orderRepository.save(order);
    }
    
    @Override
    public void completeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getPaymentStatus() != PaymentStatus.PAID) {
            throw new RuntimeException("Only PAID orders can be completed");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);

        orderRepository.save(order);
    }
    
    @Override
    public Page<Order> getOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }
}