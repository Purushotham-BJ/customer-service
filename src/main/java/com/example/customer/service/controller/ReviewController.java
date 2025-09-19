package com.example.customer.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.customer.service.model.Review;
import com.example.customer.service.repository.ReviewRepository;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    // Create Review
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        review.setCreatedAt(LocalDateTime.now());
        log.info("New review created for product {} by user {}", review.getProductId(), review.getUserId());
        return reviewRepository.save(review);
    }

    // Get all reviews
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Get review by ID
    @GetMapping("/{id}")
    public Optional<Review> getReviewById(@PathVariable String id) {
        return reviewRepository.findById(id);
    }

    // Get reviews by product
    @GetMapping("/product/{productId}")
    public List<Review> getReviewsByProduct(@PathVariable String productId) {
        return reviewRepository.findByProductId(productId);
    }

    // Get reviews by user
    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable String userId) {
        return reviewRepository.findByUserId(userId);
    }

    // Delete review
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable String id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            log.error("Review not found: {}", id);
            return "Review not found";
        }
        reviewRepository.deleteById(id);
        log.info("Review deleted: {}", id);
        return "Review deleted successfully";
    }
}
