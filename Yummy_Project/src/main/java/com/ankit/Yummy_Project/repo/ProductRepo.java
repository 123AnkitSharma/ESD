package com.ankit.Yummy_Project.repo;

import com.ankit.Yummy_Project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findTop2ByPriceBetweenOrderByPriceAsc(Double priceStart, Double priceEnd);

}
