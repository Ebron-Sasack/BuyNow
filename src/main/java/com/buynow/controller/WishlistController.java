package com.buynow.controller;

import com.buynow.entity.Wishlist;
import com.buynow.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<Wishlist> addToWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        return new ResponseEntity<>(
                wishlistService.addToWishlist(userId, productId),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<String> removeFromWishlist(
            @PathVariable Long wishlistId) {

        wishlistService.removeFromWishlist(wishlistId);

        return ResponseEntity.ok("Product removed from wishlist successfully.");
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<Wishlist> getWishlistById(
            @PathVariable Long wishlistId) {

        return ResponseEntity.ok(
                wishlistService.getWishlistById(wishlistId));
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getAllWishlist() {

        return ResponseEntity.ok(
                wishlistService.getAllWishlist());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlistByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                wishlistService.getWishlistByUser(userId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Wishlist>> getWishlistByProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                wishlistService.getWishlistByProduct(productId));
    }
}