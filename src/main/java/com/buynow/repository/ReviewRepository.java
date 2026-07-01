package com.buynow.repository;

import com.buynow.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findByProductId(Long productId);

    Optional<List<Review>> findByUserId(Long userId);

}