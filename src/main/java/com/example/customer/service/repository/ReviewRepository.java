package com.example.customer.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.customer.service.model.Review;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByProductId(String productId);
    List<Review> findByUserId(String userId);
}
