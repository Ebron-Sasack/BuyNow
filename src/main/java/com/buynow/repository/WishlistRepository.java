package com.buynow.repository;

import com.buynow.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<List<Wishlist>> findByUserId(Long userId);

    Optional<List<Wishlist>> findByProductId(Long productId);

    Optional<Wishlist> findByUserIdAndProductId(Long userId, Long productId);
}