package com.ankit.Yummy_Project.service;

import com.ankit.Yummy_Project.dto.ProductRequest;
import com.ankit.Yummy_Project.dto.ProductResponse;
import com.ankit.Yummy_Project.entity.Product;
import com.ankit.Yummy_Project.mapper.ProductMapper;
import com.ankit.Yummy_Project.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public String createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        productRepo.save(product);
        return "Product Created Successfully";
    }

    public Product getProduct(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + id));
    }

    public ProductResponse getProductById(Long id) {
        Product product = getProduct(id);
        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepo.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : products) {
            productResponses.add(productMapper.toProductResponse(product));
        }

        return productResponses;
    }


    public String deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepo.delete(product);
        return "Product Deleted Successfully";
    }

    public String updateProduct(Long id, ProductRequest request) {
        Product product = getProduct(id);

        // Update product details
        product.setName(request.name());
        product.setPrice(request.price());

        productRepo.save(product);

        return "Product Updated Successfully";
    }

    public List<ProductResponse> getTop2ProductsByPriceRange(Double priceStart, Double priceEnd) {
        List<Product> products = productRepo.findTop2ByPriceBetweenOrderByPriceAsc(priceStart, priceEnd);

        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(productMapper.toProductResponse(product));
        }

        return productResponses;
    }


}
