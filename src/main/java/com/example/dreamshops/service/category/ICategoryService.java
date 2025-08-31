package com.example.dreamshops.service.category;

import com.example.dreamshops.dto.CategoryDTO;
import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {
    CategoryDTO getCategoryById(Long id);

    CategoryDTO getCategoryByName(String name);

    List<CategoryDTO> getAllCategories();

    CategoryDTO addCategory(Category category)throws AlreadyExistsException;

    CategoryDTO updateCategory(Long id, Category category);

    void deleteCategoryById(Long id);
}
