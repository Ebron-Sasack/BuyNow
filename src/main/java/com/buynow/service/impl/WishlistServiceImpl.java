package com.buynow.service.impl;

import com.buynow.entity.Product;
import com.buynow.entity.User;
import com.buynow.entity.Wishlist;
import com.buynow.exception.AlreadyExistsException;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.ProductRepository;
import com.buynow.repository.UserRepository;
import com.buynow.repository.WishlistRepository;
import com.buynow.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public Wishlist addToWishlist(Long userId, Long productId) {

        if (wishlistRepository.findByUserIdAndProductId(userId, productId).isPresent()) {
            throw new AlreadyExistsException("Product already exists in wishlist.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);

        return wishlistRepository.save(wishlist);
    }

    @Override
    public void removeFromWishlist(Long wishlistId) {

        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found"));

        wishlistRepository.delete(wishlist);
    }

    @Override
    public Wishlist getWishlistById(Long wishlistId) {

        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found"));
    }

    @Override
    public List<Wishlist> getAllWishlist() {
        return wishlistRepository.findAll();
    }

    @Override
    public List<Wishlist> getWishlistByUser(Long userId) {
        return wishlistRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
    }

    @Override
    public List<Wishlist> getWishlistByProduct(Long productId) {
        return wishlistRepository.findByProductId(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")
        );
    }
}