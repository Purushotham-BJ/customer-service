package com.example.customer.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import com.example.customer.service.model.Customer;
import com.example.customer.service.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    // ✅ Create a new customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created: {}", savedCustomer.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    // ✅ Get all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        log.info("Fetched {} customers", customers.size());
        return ResponseEntity.ok(customers);
    }

    // ✅ Get customer by phone
    @GetMapping("/phone/{phone}")
    public ResponseEntity<Customer> getUserByPhone(@PathVariable String phone) {
        Customer customer = customerRepository.findByPhone(phone);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            log.warn("Customer not found with phone: {}", phone);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ✅ Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Customer not found: {}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    // ✅ Update a customer by ID
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setName(updatedCustomer.getName());
                    existingCustomer.setEmail(updatedCustomer.getEmail());
                    existingCustomer.setPhone(updatedCustomer.getPhone());
                    Customer saved = customerRepository.save(existingCustomer);
                    log.info("Customer updated: {}", id);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> {
                    log.warn("Customer not found for update: {}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    // ✅ Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            log.info("Customer deleted: {}", id);
            return ResponseEntity.ok("Customer deleted successfully");
        } else {
            log.warn("Customer not found: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }
}
