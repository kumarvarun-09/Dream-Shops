package com.example.dreamshops.dto;

import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Image;
import com.example.dreamshops.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String brand;
    private String name;
    private String description;
    private BigDecimal price;
    private int inventory;

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

        List<ImageDTO> images = p.getImages().stream().map(ImageDTO::new).toList();
    }
}
