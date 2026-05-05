package com.thenocturn.pos.dto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TopProductDTO {
    private String productName;
    private Long totalSold;
}