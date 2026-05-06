package com.thenocturn.pos.service.impl;

import com.thenocturn.pos.dto.OrderResponse;
import com.thenocturn.pos.service.EmailService;
import com.thenocturn.pos.service.PdfService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	private final PdfService pdfService;

	public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, PdfService pdfService) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
		this.pdfService = pdfService;
	}

	@Override
	@Async
	public void sendOrderEmailWithInvoice(String toEmail, OrderResponse order) {

		try {
			// 🧠 STEP 1: Prepare HTML template
			Context context = new Context();
			context.setVariable("customerName", order.getCustomerName());
			context.setVariable("orderNumber", order.getOrderNumber());
			context.setVariable("items", order.getItems());
			context.setVariable("totalAmount", order.getTotalAmount());

			String htmlContent = templateEngine.process("order-confirmation", context);

			// 🧠 STEP 2: Generate PDF
//            byte[] pdfBytes = pdfService.generateInvoice(order);

			// 🧠 STEP 3: Create email
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					"UTF-8");

			helper.setTo(toEmail);
			helper.setSubject("Order Confirmation - " + order.getOrderNumber());

			// ✅ HTML body
			helper.setText(htmlContent, true);

			byte[] pdfBytes = pdfService.generateInvoice(order);

			System.out.println("PDF SIZE: " + pdfBytes.length); // debug

			// ✅ Attach PDF
			helper.addAttachment("invoice-" + order.getOrderNumber() + ".pdf", new ByteArrayResource(pdfBytes),
					"application/pdf");

			mailSender.send(message);

		} catch (Exception e) {
			throw new RuntimeException("Failed to send email with invoice");
		}
	}
}