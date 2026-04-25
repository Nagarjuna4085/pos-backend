package com.thenocturn.pos.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.thenocturn.pos.dto.ProductRequest;
import com.thenocturn.pos.entity.Category;
import com.thenocturn.pos.entity.Product;
import com.thenocturn.pos.repository.CategoryRepository;
import com.thenocturn.pos.repository.ProductRepository;
import com.thenocturn.pos.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	public ProductServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Product createProduct(ProductRequest request) {
		Category category = categoryRepository.findById(request.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Category not found"));
		
		Product product = Product.builder()
	            .name(request.getName())
	            .sku(request.getSku())
	            .barcode(request.getBarcode())
	            .price(request.getPrice())
	            .costPrice(request.getCostPrice())
	            .quantity(request.getQuantity())
	            .description(request.getDescription())
	            .category(category)
	            .isActive(true)
	            .build();
		
		if(productRepository.findBySku(product.getSku()).isPresent()) {
		    throw new RuntimeException("SKU already exists");
		}
//		product.setIsActive(true);
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product updateProduct(Long id, ProductRequest request) {
		
		Category category = categoryRepository.findById(request.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Category not found"));
		Product existing = getProductById(id);

		existing.setSku(request.getSku());
		existing.setBarcode(request.getBarcode());
		existing.setPrice(request.getPrice());
		existing.setCostPrice(request.getCostPrice());
		existing.setQuantity(request.getQuantity());
		existing.setCategory(category);
		existing.setDescription(request.getDescription());

		return productRepository.save(existing);
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = getProductById(id);
		product.setIsActive(false); // soft delete
		productRepository.save(product);
	}

	@Override
	public Product getBySku(String sku) {
		return productRepository.findBySku(sku).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	@Override
	public List<Product> searchByName(String name) {
		return productRepository.findByNameContainingIgnoreCase(name);
	}
}