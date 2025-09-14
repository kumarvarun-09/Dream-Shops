package com.example.dreamshops.service.order;

import com.example.dreamshops.dto.OrderDTO;
import com.example.dreamshops.dto.OrderItemsDTO;
import com.example.dreamshops.dto.UserDTO;
import com.example.dreamshops.enums.OrderStatus;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.Order;
import com.example.dreamshops.model.OrderItems;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.order.OrderRepository;
import com.example.dreamshops.repository.product.ProductRepository;
import com.example.dreamshops.service.cart.ICartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

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
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(order.getOrderId());
        return savedOrder;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
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

    @Override
    public List<OrderDTO> getConvertedOrderDTOs(List<Order> userOrders) {
        return userOrders.stream()
                .map(this::convertToDTO)
                .toList();
    }


    @Override
    public OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setOrderItems(
                convertToDTO(order.getOrderItems())
        );
        orderDTO.setUser(
                modelMapper.map(order.getUser(), UserDTO.class)
        );
        return orderDTO;
    }

    public List<OrderItemsDTO> convertToDTO(Set<OrderItems> orderItems) {
        if (orderItems != null) {
            return orderItems.stream()
                    .map(orderItem ->
                            modelMapper.map(orderItem, OrderItemsDTO.class))
                    .toList();
        }
        return null;
    }

}
