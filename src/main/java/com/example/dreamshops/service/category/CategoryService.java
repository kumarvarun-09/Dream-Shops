package com.example.dreamshops.service.category;

import com.example.dreamshops.dto.CategoryDTO;
import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    public final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    CategoryDTO convertToDTO;

    @Override
    public Category getCategoryById(Long id) throws ResourceNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")
                );
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
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
    public void deleteCategoryById(Long id) throws ResourceNotFoundException {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found");
                });
    }

    @Override
    public List<CategoryDTO> getConvertedCategoryDTOs(List<Category> categories) {
        return categories.stream().map(this::convertToDTO).toList();
    }

    @Override
    public CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
}
