package com.buynow.controller;

import com.buynow.entity.Review;
import com.buynow.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<Review> addReview(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestBody Review review) {

        return new ResponseEntity<>(
                reviewService.addReview(userId, productId, review),
                HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestBody Review review) {

        return ResponseEntity.ok(
                reviewService.updateReview(reviewId, review));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok("Review deleted successfully.");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(
                reviewService.getReviewById(reviewId));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {

        return ResponseEntity.ok(
                reviewService.getAllReviews());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByProduct(productId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByUser(userId));
    }
}