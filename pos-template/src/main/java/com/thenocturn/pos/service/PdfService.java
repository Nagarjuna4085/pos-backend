package com.thenocturn.pos.service;

import com.thenocturn.pos.dto.OrderResponse;

public interface PdfService {
    byte[] generateInvoice(OrderResponse order);


}
