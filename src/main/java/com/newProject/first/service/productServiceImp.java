package com.newProject.first.service;

import com.newProject.first.DAO.productRepository;
import com.newProject.first.DTO.productRequest;
import com.newProject.first.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class productServiceImp implements productService {

    private productRepository productRepository;

    @Autowired
    public productServiceImp(com.newProject.first.DAO.productRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void addProduct(productRequest product) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());

        productRepository.save(newProduct);
    }

    @Override
    public Optional<Product> findProductByID(int id) {
//        if (!productRepository.existsById(id)) {
//            throw new RuntimeException("NOT FOUND");
//        }
        Optional<Product> product = productRepository.findById(id);
        return product;
    }

    @Override
    public Page<Product> findAllFilterByPrice(Integer minPrice, Integer maxPrice, Pageable pageable) {

        return productRepository.findByPrice(
                minPrice==null? 0 : minPrice,
                maxPrice==null? Integer.MAX_VALUE : maxPrice,
                pageable
        ) ;
    }
}
