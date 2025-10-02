package com.newProject.first.service;

import com.newProject.first.DTO.ordersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface orderService {
    Page<ordersResponse> findOrders(String email, Pageable pageable);

    void save(String email);
}
