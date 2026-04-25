package com.thenocturn.pos.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}