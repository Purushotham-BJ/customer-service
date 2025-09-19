package com.example.customer.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.customer.service.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email); // custom finder
}
