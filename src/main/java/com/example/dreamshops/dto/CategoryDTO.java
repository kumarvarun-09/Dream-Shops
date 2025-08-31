package com.example.dreamshops.dto;

import com.example.dreamshops.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(Category c) {
        this.id = c.getId();
        this.name = c.getName();
    }
}
