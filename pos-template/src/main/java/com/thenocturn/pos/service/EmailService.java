package com.thenocturn.pos.service;

import com.thenocturn.pos.dto.OrderResponse;

public interface EmailService {
//    void sendOrderConfirmation(String toEmail, String orderNumber, String customerName);
	void sendOrderConfirmationHtml(String toEmail, OrderResponse order);

}
