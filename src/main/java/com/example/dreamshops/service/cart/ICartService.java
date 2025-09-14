package com.example.dreamshops.service.cart;

import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);

    Cart initializeNewCart(User user);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart createNewCart();

    Cart getCartByUserId(Long userId) throws ResourceNotFoundException;
}
