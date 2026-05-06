package com.thenocturn.pos.service.impl;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.thenocturn.pos.dto.OrderResponse;
import com.thenocturn.pos.service.PdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] generateInvoice(OrderResponse order) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // TITLE
            document.add(new Paragraph("INVOICE")
                    .setBold()
                    .setFontSize(20));

            document.add(new Paragraph("Order Number: " + order.getOrderNumber()));
            document.add(new Paragraph("Customer: " + order.getCustomerName()));
            document.add(new Paragraph("\n"));

            // TABLE
            Table table = new Table(3);
            table.addCell("Product");
            table.addCell("Qty");
            table.addCell("Price");

            order.getItems().forEach(item -> {
                table.addCell(item.getProductName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(item.getUnitPrice().toString());
            });

            document.add(table);

            document.add(new Paragraph("\nTotal: " + order.getTotalAmount())
                    .setBold());

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed");
        }
    }
}