package com.thenocturn.pos.service;


import java.util.List;

import com.thenocturn.pos.dto.ProductRequest;
import com.thenocturn.pos.entity.Product;

public interface ProductService {

    Product createProduct(ProductRequest request);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    Product getBySku(String sku);

    List<Product> searchByName(String name);
}