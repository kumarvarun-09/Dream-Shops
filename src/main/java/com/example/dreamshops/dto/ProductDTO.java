package com.example.dreamshops.dto;

import com.example.dreamshops.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String brand;
    private String name;
    private String description;
    private BigDecimal price;
    private Long inventory;

    private CategoryDTO category;
    private List<ImageDTO> images;

    public ProductDTO(Product p) {
        this.id = p.getId();
        this.brand = p.getBrand();
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.inventory = p.getInventory();
        this.category = new CategoryDTO(p.getCategory());
        var productImages = p.getImages();
        if (productImages != null) {
            images = p.getImages().stream().map(ImageDTO::new).toList();
        }
    }
}
