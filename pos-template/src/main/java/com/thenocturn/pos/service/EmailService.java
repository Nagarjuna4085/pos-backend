package com.thenocturn.pos.service;

public interface EmailService {
    void sendOrderConfirmation(String toEmail, String orderNumber, String customerName);

}
