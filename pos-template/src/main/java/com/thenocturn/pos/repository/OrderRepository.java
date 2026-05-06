package com.thenocturn.pos.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thenocturn.pos.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = 'COMPLETED'")
	BigDecimal getTotalRevenue();

	@Query("SELECT COUNT(o) FROM Order o")
	Long getTotalOrders();

	@Query("""
			SELECT DATE(o.createdAt), SUM(o.totalAmount), COUNT(o)
			FROM Order o
			WHERE o.orderStatus = 'COMPLETED'
			GROUP BY DATE(o.createdAt)
			""")
	List<Object[]> getDailySalesReport();
	
	Page<Order> findAll(Pageable pageable);
	
}