package com.example.dreamshops.dto;

import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

        Long cartId;
        Long cartItem;
}
