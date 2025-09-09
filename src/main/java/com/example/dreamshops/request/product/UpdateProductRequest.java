package com.example.dreamshops.request.product;

import com.example.dreamshops.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    private String brand;
    private String name;
    private String description;
    private BigDecimal price;
    private Long inventory;
    private Category category;

}