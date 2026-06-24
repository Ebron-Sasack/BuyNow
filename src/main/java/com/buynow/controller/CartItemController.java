package com.buynow.controller;

import com.buynow.entity.Cart;
import com.buynow.entity.User;
import com.buynow.payload.ApiResponse;
import com.buynow.service.CartItemService;
import com.buynow.service.CartService;
import com.buynow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/cartitems")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){
            User user = userService.getUserById(1L);
            Cart cart = cartService.initializeNewCart(user);
            cartItemService.addItemToCart(cart.getId(), productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Sucess",null));
    }

    @DeleteMapping("/item/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@RequestParam Long cartId,@RequestParam Long productId){
            cartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(new ApiResponse("Item Removed Sucess",null));
    }


    @PutMapping("/item/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestParam Long cartId,
                                                          @RequestParam Long productId,
                                                          @RequestParam Integer quantity){
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item Removed Sucess",null));
    }
}
