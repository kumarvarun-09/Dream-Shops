package com.example.dreamshops.dto;


import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.CartItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CartDTO {
    Long cartId;
    BigDecimal totalAmount;
    Set<CartItemDTO> cartItems;

    public CartDTO(Cart cart) {
        this.cartId = cart.getId();
        this.totalAmount = cart.calculateTotalAmount();
        Set<CartItem> cartItemsOrg = cart.getCartItems();
        if(cartItemsOrg != null){
            cartItems = new HashSet<>();
            for(CartItem c: cartItemsOrg)
            {
                cartItems.add(new CartItemDTO(c));
            }
        }
    }
}
