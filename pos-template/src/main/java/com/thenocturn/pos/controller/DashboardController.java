package com.thenocturn.pos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thenocturn.pos.dto.DashboardResponse;
import com.thenocturn.pos.dto.SalesReportResponse;
import com.thenocturn.pos.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }
    
    @GetMapping("/sales-report")
    public List<SalesReportResponse> getSalesReport() {
        return dashboardService.getSalesReport();
    }
}
