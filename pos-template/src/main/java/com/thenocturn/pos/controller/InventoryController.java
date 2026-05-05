package com.thenocturn.pos.controller;

import com.thenocturn.pos.dto.AddStockRequest;
import com.thenocturn.pos.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add-stock")
    public String addStock(@RequestBody AddStockRequest request) {
        inventoryService.addStock(
                request.getProductId(),
                request.getQuantity(),
                request.getReason()
        );
        return "Stock added successfully";
    }
    
    @PostMapping("/remove-stock")
    public String removeStock(@RequestBody AddStockRequest request) {
        inventoryService.removeStock(
                request.getProductId(),
                request.getQuantity(),
                request.getReason()
        );
        return "Stock removed successfully";
    }
}