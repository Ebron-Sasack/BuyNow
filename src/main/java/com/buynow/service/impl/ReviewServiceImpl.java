package com.buynow.service.impl;

import com.buynow.entity.Product;
import com.buynow.entity.Review;
import com.buynow.entity.User;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.ProductRepository;
import com.buynow.repository.ReviewRepository;
import com.buynow.repository.UserRepository;
import com.buynow.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public Review addReview(Long userId, Long productId, Review review) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        review.setUser(user);
        review.setProduct(product);

        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long reviewId, Review review) {

        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());

        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    @Override
    public List<Review> getAllReviews() {

        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByProduct(Long productId) {

        return reviewRepository.findByProductId(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")
        );
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
    }
}