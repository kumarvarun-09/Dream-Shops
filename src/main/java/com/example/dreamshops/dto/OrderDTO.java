package com.example.dreamshops.dto;

import com.example.dreamshops.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private BigDecimal totaAmount;
    private LocalDate timePlaced;
    private OrderStatus orderStatus;
    private List<OrderItemsDTO> orderItems;
    private UserDTO user;
}
