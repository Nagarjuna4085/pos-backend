package com.thenocturn.pos.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thenocturn.pos.dto.OrderRequest;
import com.thenocturn.pos.dto.OrderResponse;
import com.thenocturn.pos.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // CREATE ORDER
    @PostMapping
    public OrderResponse  createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }
}