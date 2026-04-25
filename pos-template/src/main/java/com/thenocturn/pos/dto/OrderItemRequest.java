package com.thenocturn.pos.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderItemRequest {

    private Long productId;

    private Integer quantity;

    private BigDecimal unitPrice; 
}