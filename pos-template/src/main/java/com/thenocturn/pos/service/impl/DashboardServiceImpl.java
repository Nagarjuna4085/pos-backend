package com.thenocturn.pos.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thenocturn.pos.dto.DashboardResponse;
import com.thenocturn.pos.dto.LowStockDTO;
import com.thenocturn.pos.dto.SalesReportResponse;
import com.thenocturn.pos.repository.OrderRepository;
import com.thenocturn.pos.repository.ProductRepository;
import com.thenocturn.pos.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	public DashboardServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	@Override
	public DashboardResponse getDashboard() {

		BigDecimal revenue = orderRepository.getTotalRevenue();
		Long totalOrders = orderRepository.getTotalOrders();

		var topProducts = productRepository.getTopSellingProducts();

		var lowStock = productRepository.findByQuantityLessThan(5).stream()
				.map(p -> LowStockDTO.builder().productName(p.getName()).quantity(p.getQuantity()).build()).toList();

		return DashboardResponse.builder().totalRevenue(revenue != null ? revenue : BigDecimal.ZERO)
				.totalOrders(totalOrders).topProducts(topProducts).lowStockProducts(lowStock).build();
	}
	
	
	
	public List<SalesReportResponse> getSalesReport() {

	    return orderRepository.getDailySalesReport()
	            .stream()
	            .map(obj -> SalesReportResponse.builder()
	                    .date(obj[0].toString())
	                    .totalSales((BigDecimal) obj[1])
	                    .totalOrders((Long) obj[2])
	                    .build())
	            .toList();
	}
}