package com.thenocturn.pos.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private BigDecimal totalSales;
    private Long totalOrders;
    private BigDecimal totalRevenue;

    private List<TopProductDTO> topProducts;
    private List<LowStockDTO> lowStockProducts;
}