package com.example.dreamshops.service.category;

import com.example.dreamshops.dto.CategoryDTO;
import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    public final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) throws ResourceNotFoundException{
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for(Category c: categories){
            categoryDTOs.add(new CategoryDTO(c.getId(), c.getName()));
        }
        return categoryDTOs;
    }

    @Override
    public Category addCategory(Category category) throws AlreadyExistsException {
        Category existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory == null) {
            return categoryRepository.save(category);
        }
        throw new AlreadyExistsException(category.getName() + " category already exists");
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found")
                );
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategoryById(Long id) throws ResourceNotFoundException{
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found");
                });
    }
}
