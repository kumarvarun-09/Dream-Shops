package com.example.dreamshops.service.category;

import com.example.dreamshops.dto.CategoryDTO;
import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category)throws AlreadyExistsException;

    Category updateCategory(Long id, Category category);

    void deleteCategoryById(Long id);

    List<CategoryDTO> getConvertedCategoryDTOs(List<Category> categories);
    CategoryDTO convertToDTO(Category category);
}
