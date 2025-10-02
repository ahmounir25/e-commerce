package com.newProject.first.service;

import com.newProject.first.DTO.productRequest;
import com.newProject.first.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface productService {
    Page<Product> findAll(Pageable pageable);
    void addProduct(productRequest product);
    Optional<Product> findProductByID(int id);

    Page<Product> findAllFilterByPrice(Integer minPrice, Integer maxPrice, Pageable pageable);
}
