package com.thenocturn.pos.service;

import com.thenocturn.pos.entity.Product;

public interface InventoryService {

    void addStock(Long productId, Integer quantity, String reason);

    void removeStock(Long productId, Integer quantity, String reason);
}