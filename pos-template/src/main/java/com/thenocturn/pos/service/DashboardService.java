package com.thenocturn.pos.service;

import java.util.List;

import com.thenocturn.pos.dto.DashboardResponse;
import com.thenocturn.pos.dto.SalesReportResponse;

public interface DashboardService {
    DashboardResponse getDashboard();

	List<SalesReportResponse> getSalesReport();
}