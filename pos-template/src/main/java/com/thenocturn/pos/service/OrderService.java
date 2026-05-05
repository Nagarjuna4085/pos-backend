package com.thenocturn.pos.service;

import com.thenocturn.pos.dto.OrderRequest;
import com.thenocturn.pos.dto.OrderResponse;

public interface OrderService {

	OrderResponse createOrder(OrderRequest request);
	void cancelOrder(Long orderId);
	void markOrderAsPaid(Long orderId);
	void completeOrder(Long orderId);
}