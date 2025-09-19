package com.example.customer.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.customer.service.model.Order;
import com.example.customer.service.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Create new Order
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        order.setCreatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // Get All Orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get Order by ID
    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable String id) {
        return orderRepository.findById(id);
    }

    // Get Orders by User
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable String userId) {
        return orderRepository.findByUserId(userId);
    }

    // Update Order Status
    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable String id, @RequestBody String status) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(status);
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Delete Order
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable String id) {
        orderRepository.deleteById(id);
        return "Order deleted successfully";
    }
}
