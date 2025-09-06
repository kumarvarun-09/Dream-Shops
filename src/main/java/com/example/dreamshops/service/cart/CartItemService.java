package com.example.dreamshops.service.cart;

import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.CartItem;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.cart.CartItemRepository;
import com.example.dreamshops.repository.cart.CartRepository;
import com.example.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    public final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;
    private final IProductService productService;

    @Override
    public CartItem addItemToCart(Long cartId, Long productId, Long quantity) throws ResourceNotFoundException{
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem;
        try {
            cartItem = getCartItem(cartId, productId);
        } catch (ResourceNotFoundException e) {
            cartItem = new CartItem();
        }
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem = cartItemRepository.save(cartItem);
        cart.addItem(cartItem);
        cartRepository.save(cart);
        return cartItem;
    }

    @Override
    public CartItem updateItemInCart(Long cartId, Long productId, Long quantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem item = getCartItem(cartId, productId);
        item.setUnitPrice(item.getProduct().getPrice());
        item.setQuantity(quantity);
        item.calculateTotalPrice();
        item = cartItemRepository.save(item);
        cartRepository.save(cart);
        return item;
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) throws ResourceNotFoundException {
        return cartService.getCart(cartId)
                .getCartItems()
                .stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
