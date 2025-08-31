package com.example.dreamshops.service.product;

import com.example.dreamshops.dto.ProductDTO;
import com.example.dreamshops.exceptions.ProductNotFoundException;
import com.example.dreamshops.request.AddProductRequest;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.category.CategoryRepository;
import com.example.dreamshops.repository.product.ProductRepository;
import com.example.dreamshops.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor  // Constructor Injection
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO getProductById(Long id) {
        return new ProductDTO(productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found")
                ));

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
    public ProductDTO addProduct(AddProductRequest request) {
        Category category = categoryRepository.findByName(request.getCategory().getName());
        if (category == null) {
            categoryRepository.save(new Category(request.getCategory().getName()));
            category = categoryRepository.findByName(request.getCategory().getName());
        }
        // If category is already present in db, we just save the product
        // Else firstly we save the category into the db, then we save product
        return new ProductDTO(productRepository.save(createProduct(request, category)));
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
    public ProductDTO updateProduct(Long id, UpdateProductRequest request) throws ProductNotFoundException {
        Product existingProduct = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Category category = null;
        if (request != null
                && request.getCategory() != null
                && request.getCategory().getName() != null
                && !request.getCategory().getName().isEmpty()) {
            category = categoryRepository.findByName(request.getCategory().getName());
        }
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
        return new ProductDTO(productRepository.save(existingProduct));
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
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product p: products)
        {
            productDTOs.add(new ProductDTO(p));
        }
        return productDTOs;
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<Product> existingProducts = productRepository.findByCategoryId(categoryId);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product p: existingProducts){
            productDTOS.add(new ProductDTO(p));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> existingProducts = productRepository.findByCategoryName(category);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product p: existingProducts){
            productDTOS.add(new ProductDTO(p));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getProductsByBrand(String brand) {
        List<Product> existingProducts = productRepository.findByBrand(brand);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product p: existingProducts){
            productDTOS.add(new ProductDTO(p));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> existingProducts = productRepository.findByCategoryNameAndBrand(category, brand);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product p: existingProducts){
            productDTOS.add(new ProductDTO(p));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getProductsByName(String name) {
        List<Product> existingProducts = productRepository.findByName(name);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product p: existingProducts){
            productDTOS.add(new ProductDTO(p));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getProductsByBrandAndName(String brand, String name) {
        List<Product> existingProducts = productRepository.findByBrandAndName(brand, name);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product p: existingProducts){
            productDTOS.add(new ProductDTO(p));
        }
        return productDTOS;
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
