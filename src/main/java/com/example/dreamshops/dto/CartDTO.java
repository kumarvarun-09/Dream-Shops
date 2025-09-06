package com.example.dreamshops.dto;


import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.CartItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {
    Long cartId;
    List<Long> cartItems;

    public CartDTO() {
        this.cartItems = new ArrayList<>();
    }

    public CartDTO(CartItem item) {
        this.cartId = item.getCart().getId();
        this.cartItems = new ArrayList<>();
        this.cartItems.add(item.getId());
    }

    public CartDTO(Cart cart) {
        this.cartItems = new ArrayList<>();
        this.cartId = cart.getId();
        if (cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {
                this.cartItems.add(item.getId());
            }
        }
    }
}
