package com.example.dreamshops.controller;

import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.response.ApiResponse;
import com.example.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Success",
                            orderService.convertToDTO(
                                    orderService.placeOrder(userId)
                            )
                    )
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable(name = "id") Long orderId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Found", orderService.getOrder(orderId))
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/user/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        {
            try {
                return ResponseEntity.ok(
                        new ApiResponse("Orders of user: " + userId,
                                orderService.getConvertedOrderDTOs(
                                        orderService.getUserOrders(userId)
                                )
                        )
                );
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(e.getMessage(), NOT_FOUND));
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
            }
        }
    }
}
