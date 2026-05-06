package com.thenocturn.pos.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.thenocturn.pos.dto.ProductRequest;
import com.thenocturn.pos.entity.Product;

public interface ProductService {
	String uploadImage(MultipartFile file);

    Product createProduct(ProductRequest request);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    Product getBySku(String sku);

    List<Product> searchByName(String name);

	Page<Product> getProducts(int page, int size);
}