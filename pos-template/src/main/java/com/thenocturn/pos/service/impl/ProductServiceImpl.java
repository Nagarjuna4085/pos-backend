package com.thenocturn.pos.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.thenocturn.pos.entity.Product;
import com.thenocturn.pos.repository.ProductRepository;
import com.thenocturn.pos.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Product createProduct(Product product) {
		if(productRepository.findBySku(product.getSku()).isPresent()) {
		    throw new RuntimeException("SKU already exists");
		}
		product.setIsActive(true);
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
	public Product updateProduct(Long id, Product product) {
		Product existing = getProductById(id);

		existing.setName(product.getName());
		existing.setSku(product.getSku());
		existing.setBarcode(product.getBarcode());
		existing.setPrice(product.getPrice());
		existing.setCostPrice(product.getCostPrice());
		existing.setQuantity(product.getQuantity());
		existing.setCategory(product.getCategory());
		existing.setDescription(product.getDescription());
		existing.setIsActive(product.getIsActive());

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