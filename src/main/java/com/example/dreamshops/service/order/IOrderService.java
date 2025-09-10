package com.example.dreamshops.service.order;

import com.example.dreamshops.dto.OrderDTO;
import com.example.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);

    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);

    OrderDTO convertToDTO(Order order);

    List<OrderDTO> getConvertedOrderDTOs(List<Order> userOrders);
}
