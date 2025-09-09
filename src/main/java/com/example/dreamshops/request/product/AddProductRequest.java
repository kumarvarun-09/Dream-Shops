package com.example.dreamshops.request.product;


import com.example.dreamshops.model.Category;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
    private String brand;
    private String name;
    private String description;
    private BigDecimal price;
    private Long inventory;
    private Category category;

}
