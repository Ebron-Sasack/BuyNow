package com.buynow.service;

import com.buynow.entity.CartItem;

public interface CartItemService {
    void addItemToCart(Long cartId, Long productId, Integer quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, Integer quantity);

    CartItem getCartItems(Long cartId, Long productId);
}
