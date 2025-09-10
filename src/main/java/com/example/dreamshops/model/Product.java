package com.example.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String name;
    private String description;
    private BigDecimal price;
    private Long inventory;

    @ManyToOne(cascade = CascadeType.ALL) // This means: Many Products can belong to one Category
    @JoinColumn(name = "category_id") // This tells JPA to use 'category_id' column for reaching to Category table
    private Category category;

    // This means inside images table, there will be a column named 'product', which will be used to indicate the relationship between images table and product table
    // One Product can have multiple Images.
    //
    //cascade = CascadeType.ALL means operations on Product will cascade to Image (e.g., save, delete).
    //
    //orphanRemoval = true means if an Image is removed from the list, it will be deleted from the DB.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public Product(String brand, String name, String description, BigDecimal price, Long inventory, Category category) {
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.category = category;
    }
}
