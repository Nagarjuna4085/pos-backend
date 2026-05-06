package com.thenocturn.pos.service.impl;

import com.thenocturn.pos.dto.OrderResponse;
import com.thenocturn.pos.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender,
                            TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendOrderConfirmationHtml(String toEmail, OrderResponse order) {

        try {
            Context context = new Context();
            context.setVariable("customerName", order.getCustomerName());
            context.setVariable("orderNumber", order.getOrderNumber());
            context.setVariable("items", order.getItems());
            context.setVariable("totalAmount", order.getTotalAmount());

            String htmlContent = templateEngine.process("order-confirmation", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Order Confirmation - " + order.getOrderNumber());
            helper.setText(htmlContent, true); // TRUE = HTML

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email");
        }
    }
}