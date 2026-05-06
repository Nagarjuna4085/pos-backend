package com.thenocturn.pos.service;

import org.springframework.data.domain.Page;

import com.thenocturn.pos.dto.OrderRequest;
import com.thenocturn.pos.dto.OrderResponse;
import com.thenocturn.pos.entity.Order;

public interface OrderService {

	OrderResponse createOrder(OrderRequest request);
	void cancelOrder(Long orderId);
	void markOrderAsPaid(Long orderId);
	void completeOrder(Long orderId);
	Page<Order> getOrders(int page, int size);
}