package com.thenocturn.pos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String sku;

	private String barcode;

	@Column(nullable = false)
	private BigDecimal price;

	private BigDecimal costPrice;

	private Integer quantity;

	private String category;

	private String description;

	private Boolean  isActive = true;
	
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String createdBy;
}
