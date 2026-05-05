package com.thenocturn.pos.service.impl;

import com.thenocturn.pos.entity.*;
import com.thenocturn.pos.repository.InventoryRepository;
import com.thenocturn.pos.repository.ProductRepository;
import com.thenocturn.pos.service.InventoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }
    @Override
    public void addStock(Long productId, Integer quantity, String reason) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);

        InventoryLog log = InventoryLog.builder()
                .product(product)
                .type(InventoryType.IN)
                .quantity(quantity)
                .reason(reason)
                .createdAt(LocalDateTime.now())
                .build();

        inventoryRepository.save(log);
    }

    @Override
    public void removeStock(Long productId, Integer quantity, String reason) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if(product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        InventoryLog log = InventoryLog.builder()
                .product(product)
                .type(InventoryType.OUT)
                .quantity(quantity)
                .reason(reason)
                .createdAt(LocalDateTime.now())
                .build();

        inventoryRepository.save(log);
    }
    
  
}