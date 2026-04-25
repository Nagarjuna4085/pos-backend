package com.thenocturn.pos.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private BigDecimal totalAmount;

    private List<OrderItemResponse> items;
}