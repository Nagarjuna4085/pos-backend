package com.thenocturn.pos.repository;

import com.thenocturn.pos.entity.InventoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryLog, Long> {
}