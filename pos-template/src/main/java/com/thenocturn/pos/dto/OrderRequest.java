package com.thenocturn.pos.dto;

import java.math.BigDecimal;
import java.util.List;

import com.thenocturn.pos.entity.PaymentMethod;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderRequest {

    private String customerName;

    private String customerPhone;

    private BigDecimal discount;

    private BigDecimal tax;

    private PaymentMethod paymentMethod;

    private List<OrderItemRequest> items;
}