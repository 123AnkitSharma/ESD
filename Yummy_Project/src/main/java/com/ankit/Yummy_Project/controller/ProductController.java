package com.ankit.Yummy_Project.controller;

import com.ankit.Yummy_Project.dto.ProductRequest;
import com.ankit.Yummy_Project.dto.ProductResponse;
import com.ankit.Yummy_Project.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    // Get all products
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }


    //localhost:8080/api/v1/product/top2?priceStart=15&priceEnd=30
    @GetMapping("/top2")
    public ResponseEntity<List<ProductResponse>> getTop2ProductsByPriceRange(@RequestParam Double priceStart, @RequestParam Double priceEnd) {
        List<ProductResponse> products = productService.getTop2ProductsByPriceRange(priceStart, priceEnd);
        return ResponseEntity.ok(products);
    }

}
