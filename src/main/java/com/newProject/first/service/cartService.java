package com.newProject.first.service;

import com.newProject.first.DTO.cartResponse;
import com.newProject.first.DTO.itemResponse;
import com.newProject.first.entity.Cart;
import com.newProject.first.entity.cartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface cartService {

    Cart addToCart(String email, int product_id, int quantity);

    cartResponse findCart(String email);

    Page<itemResponse> findItems(String email,Pageable pageable);

    void deleteItem(int id, String email);

    itemResponse getItem(int id, String email);

    itemResponse updateItemQuantity(int id, String email, int quantity);

    void deleteItems(int cartId);

    Cart getCartWithItemsAndProducts(int id);
}
