package com.thenocturn.pos.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesReportResponse {

    private String date;
    private BigDecimal totalSales;
    private Long totalOrders;
}