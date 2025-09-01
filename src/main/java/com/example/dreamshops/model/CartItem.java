package com.example.dreamshops.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class CartItem {
    @Id
    Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    Long quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;

    public void calculateTotalPrice(){
        totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
