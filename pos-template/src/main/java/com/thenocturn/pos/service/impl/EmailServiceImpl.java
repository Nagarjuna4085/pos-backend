package com.thenocturn.pos.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.thenocturn.pos.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendOrderConfirmation(String toEmail, String orderNumber, String customerName) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Order Confirmation - " + orderNumber);

        message.setText(
                "Hello " + customerName + ",\n\n" +
                "Your order has been placed successfully.\n" +
                "Order Number: " + orderNumber + "\n\n" +
                "Thank you for shopping with us!"
        );

        mailSender.send(message);
    }
}