package com.example.dreamshops.service.order;

import com.example.dreamshops.enums.OrderStatus;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.Order;
import com.example.dreamshops.model.OrderItems;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.OrderRepository;
import com.example.dreamshops.repository.product.ProductRepository;
import com.example.dreamshops.service.cart.ICartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order with id: " + orderId + " not found")
                );
    }

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
       Cart cart = cartService.getCartByUserId(userId);
       Order order = createOrder(cart);
       List<OrderItems> orderItems = createOrderItems(order, cart);
       order.setOrderItems(new HashSet<>(orderItems));
       order.setTotaAmount(calculateTotalAmount(orderItems));
       Order savedOrder = orderRepository.save(order);
       cartService.clearCart(order.getOrderId());
        return savedOrder;
    }

    @Override
    public List<Order>getUserOrders(Long userId){
        return orderRepository.findByUserId(userId);
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTimePlaced(LocalDate.now());
        return order;
    }

    public List<OrderItems> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> {
                            Product product = cartItem.getProduct();
                            product.setInventory(product.getInventory() - cartItem.getQuantity());
                            productRepository.save(product);
                            return new OrderItems(
                                    order,
                                    product,
                                    cartItem.getQuantity(),
                                    product.getPrice()
                            );
                        }
                ).toList();
    }

    public BigDecimal calculateTotalAmount(List<OrderItems> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
