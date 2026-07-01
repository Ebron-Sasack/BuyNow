package com.buynow.service;

import com.buynow.entity.Wishlist;

import java.util.List;

public interface WishlistService {

    Wishlist addToWishlist(Long userId, Long productId);

    void removeFromWishlist(Long wishlistId);

    Wishlist getWishlistById(Long wishlistId);

    List<Wishlist> getAllWishlist();

    List<Wishlist> getWishlistByUser(Long userId);

    List<Wishlist> getWishlistByProduct(Long productId);
}