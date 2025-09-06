package com.example.dreamshops.service.cart;

import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, Long quantity);
    void updateItemInCart(Long cartId, Long productId, Long quantity);
    void removeItemFromCart(Long cartId, Long productId);

    CartItem getCartItem(Long cartId, Long productId) throws ResourceNotFoundException;
}
