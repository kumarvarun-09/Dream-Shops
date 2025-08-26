package com.example.dreamshops.exceptions;

import com.example.dreamshops.model.Product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
