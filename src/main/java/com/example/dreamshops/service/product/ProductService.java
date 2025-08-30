package com.example.dreamshops.service.product;

import com.example.dreamshops.exceptions.ProductNotFoundException;
import com.example.dreamshops.request.AddProductRequest;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.category.CategoryRepository;
import com.example.dreamshops.repository.product.ProductRepository;
import com.example.dreamshops.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor  // Constructor Injection
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found")
                );

//        You could write the same thing using an anonymous class:
//        return productRepository.findById(id).orElseThrow(new Supplier<ProductNotFoundException>() {
//            @Override
//            public ProductNotFoundException get() {
//                return new ProductNotFoundException("Product not found");
//            }
//        });
//        But the lambda () -> new ProductNotFoundException(...) is much more concise and readable.

    }

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = categoryRepository.findByName(request.getCategory().getName());
        if (category == null) {
            categoryRepository.save(new Category(request.getCategory().getName()));
            category = categoryRepository.findByName(request.getCategory().getName());
        }
        // If category is already present in db, we just save the product
        // Else firstly we save the category into the db, then we save product
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(request.getBrand(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category);
    }

    @Override
    public Product updateProduct(Long id, UpdateProductRequest request) throws ProductNotFoundException{
        Product existingProduct = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Category category = categoryRepository.findByName(request.getCategory().getName());
        if (category == null) {
            categoryRepository.save(new Category(request.getCategory().getName()));
            category = categoryRepository.findByName(request.getCategory().getName());
        }
        // If category is already present in db, we just save the product
        // Else firstly we save the category into the db, then we save product
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setCategory(category);
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, // If product is present, it will be deleted
                        () -> {
                            throw new ProductNotFoundException("Product not found"); // if product is not present, we'll throw exception
                        });
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory(categoryId);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
