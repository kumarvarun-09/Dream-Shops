package com.example.dreamshops.controller;

import com.example.dreamshops.dto.ProductDTO;
import com.example.dreamshops.exceptions.ProductNotFoundException;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.request.AddProductRequest;
import com.example.dreamshops.request.UpdateProductRequest;
import com.example.dreamshops.response.ApiResponse;
import com.example.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Products: ", productService.getAllProducts())
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Found", productService.getProductById(id))
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("product/add")
    ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            return ResponseEntity.ok(new ApiResponse("Success", productService.addProduct(product)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("product/{id}")
    ResponseEntity<ApiResponse> updateProductById(@PathVariable Long id, @RequestBody UpdateProductRequest product) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Updated", productService.updateProduct(id, product))
            );
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("product/{id}")
    ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byBrandAndName")
    ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<ProductDTO> products = productService.getProductsByBrandAndName(brandName, productName);
            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No product found with brand and name", null)
                        );
            }
            return ResponseEntity.ok(
                    new ApiResponse("Found", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byCategoryAndBrand")
    ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName) {
        try {
            List<ProductDTO> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No product found with category and brand", null)
                        );
            }
            return ResponseEntity.ok(
                    new ApiResponse("Found", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byName/{name}")
    ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        try {
            List<ProductDTO> products = productService.getProductsByName(name);
            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No product found with name", null)
                        );
            }
            return ResponseEntity.ok(
                    new ApiResponse("Found", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byBrand/{brand}")
    ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) {
        try {
            List<ProductDTO> products = productService.getProductsByBrand(brand);
            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No product found with brand name", null)
                        );
            }
            return ResponseEntity.ok(
                    new ApiResponse("Found", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byCategory/{category}")
    ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        try {
            List<ProductDTO> products = productService.getProductsByCategory(category);
            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No product found with category", null)
                        );
            }
            return ResponseEntity.ok(
                    new ApiResponse("Found", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/countByBrandAndName")
    ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            var count = productService.countProductsByBrandAndName(brandName, productName);
            return ResponseEntity.ok(
                    new ApiResponse("Product Count!", count)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
