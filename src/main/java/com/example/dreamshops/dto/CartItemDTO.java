package com.example.dreamshops.dto;

import com.example.dreamshops.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    Long cartItemId;
    Long productId;
    Long quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
    Long userId;

    public CartItemDTO(CartItem cartItem) {
        this.cartItemId = cartItem.getId();
        this.productId = cartItem.getProduct().getId();
        this.quantity = cartItem.getQuantity();
        this.unitPrice = cartItem.getUnitPrice();
        this.totalPrice = cartItem.getTotalPrice();
        this.userId = cartItem.getCart().getUser().getId();
    }

    public BigDecimal calculateTotalPrice() {
        return totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
