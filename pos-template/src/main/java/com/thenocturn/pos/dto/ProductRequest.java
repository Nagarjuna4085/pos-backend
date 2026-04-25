package com.thenocturn.pos.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String name;
    private String sku;
    private String barcode;
    private BigDecimal price;
    private BigDecimal costPrice;
    private Integer quantity;
    private String description;

    private Long categoryId;
}