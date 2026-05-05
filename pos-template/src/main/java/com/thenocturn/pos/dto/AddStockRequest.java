package com.thenocturn.pos.dto;

import lombok.Data;

@Data
public class AddStockRequest {

    private Long productId;
    private Integer quantity;
    private String reason; // PURCHASE / RESTOCK / MANUAL
}