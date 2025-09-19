package com.example.customer.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.customer.service.model.Customer;

public interface CustomerRepository  extends MongoRepository<Customer,String>{
	Customer findByEmail(String email);
	Customer findByPhone(String phone);
}
