package com.buynow.controller;

import com.buynow.entity.Cart;
import com.buynow.payload.ApiResponse;
import com.buynow.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Sucess",cart));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear Cart Sucess",null));
    }

    @GetMapping("/{cartId}/totalPrice")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long cartId){
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Total Price",totalPrice));
    }
}
