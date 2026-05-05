package com.thenocturn.pos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thenocturn.pos.dto.TopProductDTO;
import com.thenocturn.pos.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByIsActiveTrue();

    @Query("""
    		SELECT new com.thenocturn.pos.dto.TopProductDTO(
    		    oi.product.name,
    		    SUM(oi.quantity)
    		)
    		FROM OrderItem oi
    		GROUP BY oi.product.name
    		ORDER BY SUM(oi.quantity) DESC
    		""")
    		List<TopProductDTO> getTopSellingProducts();
    
    List<Product> findByQuantityLessThan(Integer threshold);
}