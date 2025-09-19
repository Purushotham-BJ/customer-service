package com.example.customer.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.customer.service.model.Product;
import com.example.customer.service.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Create Product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Get All Products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get Product by ID
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable String id) {
        return productRepository.findById(id);
    }

    // Update Product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            product.setTags(updatedProduct.getTags());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
        return "Product deleted successfully";
    }

    // Search Products by Category
    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable String category) {
        return productRepository.findByCategory(category);
    }

    // Search Products by Tag
    @GetMapping("/tag/{tag}")
    public List<Product> getByTag(@PathVariable String tag) {
        return productRepository.findByTagsContaining(tag);
    }
}
