package com.example.dreamshops.controller;

import com.example.dreamshops.dto.CartDTO;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.CartItem;
import com.example.dreamshops.response.ApiResponse;
import com.example.dreamshops.service.cart.ICartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItem")
public class CartItemController {
    public final ICartItemService cartItemService;

    @GetMapping("/add/{cartId}")
    public ResponseEntity<ApiResponse> addItemToCart(@PathVariable Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Long quantity) {
        try {
            CartItem item = cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Success",
                                    new CartDTO(item)
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

    @Transactional
    @DeleteMapping("/delete/{cartId}/item/{productId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Deleted", null)
                    );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/{cartId}/item/{productId}")
    public ResponseEntity<ApiResponse> updateItemInCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId,
                                                        @RequestParam Long quantity) {
        try {
            CartItem item = cartItemService.updateItemInCart(cartId, productId, quantity);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Updated",
                                    new CartDTO(item)
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
