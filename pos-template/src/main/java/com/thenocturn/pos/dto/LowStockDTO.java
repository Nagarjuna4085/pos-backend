package com.thenocturn.pos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LowStockDTO {
    private String productName;
    private Integer quantity;
}