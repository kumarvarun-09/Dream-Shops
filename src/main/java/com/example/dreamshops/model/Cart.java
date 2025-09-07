package com.example.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigDecimal totalAmount;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<CartItem> cartItems;

    public void addItem(CartItem item) {
        item.calculateTotalPrice();
        this.cartItems.add(item);
        item.setCart(this);
        this.calculateTotalAmount();
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
        this.calculateTotalAmount();
    }

    public BigDecimal calculateTotalAmount() {
        totalAmount = BigDecimal.valueOf(0);
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                totalAmount = totalAmount.add(cartItem.calculateTotalPrice());
            }
        }
        return totalAmount;
    }
}
