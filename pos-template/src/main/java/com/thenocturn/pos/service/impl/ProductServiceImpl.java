package com.thenocturn.pos.service.impl;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thenocturn.pos.dto.ProductRequest;
import com.thenocturn.pos.entity.Category;
import com.thenocturn.pos.entity.Product;
import com.thenocturn.pos.exception.ResourceNotFoundException;
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
		System.out.println("DEBUG 1: Request Image URL: " + request.getImageUrl()); // Check if request has it
	
		Category category = categoryRepository.findById(request.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Category not found"));
		if(request.getImageUrl() == null) throw new RuntimeException("no image data");
		
		
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
	            .imageUrl(request.getImageUrl()) // <--- Verify this line
	            .build();
		
		System.err.println("===> PRODUCT BEFORE SAVE: " + product.getImageUrl());
		
		if(productRepository.findBySku(product.getSku()).isPresent()) {
		    throw new RuntimeException("SKU already exists");
		}
		
//		
//		product.setIsActive(true);
//		return productRepository.save(product);
		
		Product savedProduct = productRepository.save(product);
	    
	    System.out.println("DEBUG 3: Saved Product Image URL: " + savedProduct.getImageUrl()); // Check if DB saved it
	    
	    return savedProduct;
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found!!"));
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
	
	@Override
	public Page<Product> getProducts(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    return productRepository.findAll(pageable);
	}
	
	
	@Override
	public String uploadImage(MultipartFile file) {

	    try {
	        String uploadDir = "uploads/";

	        String fileName = java.util.UUID.randomUUID()
	                + "_" + file.getOriginalFilename();

	        java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + fileName);

	        java.nio.file.Files.createDirectories(path.getParent());

	        java.nio.file.Files.copy(file.getInputStream(), path);

	        return "/uploads/" + fileName;

	    } catch (Exception e) {
	        throw new RuntimeException("Image upload failed");
	    }
	}
}