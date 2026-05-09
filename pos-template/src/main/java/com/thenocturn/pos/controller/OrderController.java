package com.thenocturn.pos.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thenocturn.pos.dto.OrderRequest;
import com.thenocturn.pos.dto.OrderResponse;
import com.thenocturn.pos.entity.Order;
import com.thenocturn.pos.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping
    public Page<Order> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return orderService.getOrders(page, size);
    }

    // CREATE ORDER
    @PostMapping
    public OrderResponse  createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }
    
    // cancel order
    @PutMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "Order cancelled successfully";
    }
    
    @PutMapping("/{orderId}/pay")
    public String markAsPaid(@PathVariable Long orderId) {
        orderService.markOrderAsPaid(orderId);
        return "Order marked as PAID";
    }
    
    @PutMapping("/{orderId}/complete")
    public String completeOrder(@PathVariable Long orderId) {
        orderService.completeOrder(orderId);
        return "Order COMPLETED";
    }
}