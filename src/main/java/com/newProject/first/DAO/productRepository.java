package com.newProject.first.DAO;

import com.newProject.first.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface productRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    @Query("select p from Product p where p.price >= :minPrice and p.price <= :maxPrice")
    Page<Product> findByPrice(Integer minPrice, Integer maxPrice, Pageable pageable);
}
