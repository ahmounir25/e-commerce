package com.newProject.first.controller;


import com.newProject.first.DTO.productRequest;
import com.newProject.first.entity.Product;
import com.newProject.first.service.productService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class productController {

    private productService productService;


    @Autowired
    public productController(productService productService) {
        this.productService = productService;
    }

    //TODO: IMPLEMENT FILTERING And pagination
    @GetMapping("/products")
    public ResponseEntity<Map<String,Object>> getProducts(
            @RequestParam(required = false)Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @PageableDefault(size =5) Pageable pageable) {

        Page<Product> products ;
        if (maxPrice!=null||minPrice!=null){
            products = productService.findAllFilterByPrice(minPrice,maxPrice,pageable);
        }else {
            products = productService.findAll(pageable);
        }
        Map<String,Object> response=new HashMap<>();
        response.put("products",products.getContent());
        response.put("currentPage",products.getNumber());
        response.put("totalItems",products.getTotalElements());
        response.put("totalPages",products.getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody productRequest product) {
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added Successfully");
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Optional<Product>> getAllProducts(@PathVariable int id) {
        Optional<Product> product = productService.findProductByID(id);
        return ResponseEntity.ok().body(product);
    }


}
