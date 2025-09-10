package com.example.dreamshops.service.product;

import com.example.dreamshops.dto.ProductDTO;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.request.product.AddProductRequest;
import com.example.dreamshops.request.product.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);

    Product addProduct(AddProductRequest product);

    Product updateProduct(Long id, UpdateProductRequest product);

    void deleteProduct(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategoryId(Long categoryId);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    ProductDTO convertToDTO(Product product);

    List<ProductDTO> getConvertedProductDTOs(List<Product> products);
}
