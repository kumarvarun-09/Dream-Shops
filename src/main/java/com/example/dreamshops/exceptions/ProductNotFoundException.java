package com.example.dreamshops.exceptions;

import com.example.dreamshops.model.Product;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
