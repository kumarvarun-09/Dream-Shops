package com.example.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsDTO {
    private Long orderItemId;
    private Long quantity;
    private BigDecimal price;
}
