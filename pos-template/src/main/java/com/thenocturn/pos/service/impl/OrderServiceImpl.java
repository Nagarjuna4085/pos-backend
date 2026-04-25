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
import com.thenocturn.pos.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
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
            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

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

        // STEP 5: save order (cascade saves items)
        return OrderResponse.builder()
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .items(order.getItems().stream().map(item ->
                        OrderItemResponse.builder()
                                .productName(item.getProduct().getName())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .build()
                ).toList())
                .build();
    }
}