package com.ankit.Yummy_Project.mapper;

import com.ankit.Yummy_Project.dto.ProductRequest;
import com.ankit.Yummy_Project.dto.ProductResponse;
import com.ankit.Yummy_Project.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    // Convert ProductRequest to Product entity
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }
}
