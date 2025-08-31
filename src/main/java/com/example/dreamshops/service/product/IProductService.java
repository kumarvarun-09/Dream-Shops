package com.example.dreamshops.service.product;

import com.example.dreamshops.dto.ProductDTO;
import com.example.dreamshops.request.AddProductRequest;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    ProductDTO getProductById(Long id);

    ProductDTO addProduct(AddProductRequest product);

    ProductDTO updateProduct(Long id, UpdateProductRequest product);

    void deleteProduct(Long id);

    List<ProductDTO> getAllProducts();

    List<ProductDTO> getProductsByCategoryId(Long categoryId);

    List<ProductDTO> getProductsByCategory(String category);

    List<ProductDTO> getProductsByBrand(String brand);

    List<ProductDTO> getProductsByCategoryAndBrand(String category, String brand);

    List<ProductDTO> getProductsByName(String name);

    List<ProductDTO> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

}
