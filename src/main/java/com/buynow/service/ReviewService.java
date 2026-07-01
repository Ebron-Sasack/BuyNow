package com.buynow.service;

import com.buynow.entity.Review;

import java.util.List;

public interface ReviewService {

    Review addReview(Long userId, Long productId, Review review);

    Review updateReview(Long reviewId, Review review);

    void deleteReview(Long reviewId);

    Review getReviewById(Long reviewId);

    List<Review> getAllReviews();

    List<Review> getReviewsByProduct(Long productId);

    List<Review> getReviewsByUser(Long userId);

}