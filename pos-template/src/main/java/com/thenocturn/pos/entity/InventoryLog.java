package com.thenocturn.pos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // IN or OUT
    @Enumerated(EnumType.STRING)
    private InventoryType type;

    private Integer quantity;

    private String reason; // ORDER / PURCHASE / RETURN

    private LocalDateTime createdAt;
}