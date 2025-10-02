package com.newProject.first.service;

import com.newProject.first.DAO.orderRepository;
import com.newProject.first.DTO.itemResponse;
import com.newProject.first.DTO.ordersResponse;
import com.newProject.first.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class orderServiceImpl implements orderService {

    private orderRepository orderRepository;
    private userService userService;
    private cartService cartService;


    @Autowired
    public orderServiceImpl(orderRepository orderRepository,
                            userService userService,
                            cartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public Page<ordersResponse> findOrders(String email, Pageable pageable) {
        User user = userService.findUserByEmail(email);
        // Solve N+1 Query
        Page<Order> orders = orderRepository.findOrdersWithItemsAndProducts(user.getId(), pageable);
        return orders.map(order ->
                new ordersResponse(
                        order.getId(),
                        email,
                        order.getTotalAmount(),
                        order.getOrderItems().stream().map(oi ->
                                new itemResponse(
                                        oi.getId(),
                                        oi.getProduct().getName(),
                                        oi.getPrice(),
                                        oi.getQuantity(),
                                        oi.getProduct().getId()
                                )).toList()
                )
        );
    }

    // using @transactional cause we have multiple operations on db
    @Override
    @Transactional
    public void save(String email) {
        User user = userService.findUserByEmail(email);
        // Solve N+1 Query
        Cart cart = cartService.getCartWithItemsAndProducts(user.getCart().getId());
        Order order = new Order();

        double totalAmount = cart.getCartItems().stream().
                mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
        order.setTotalAmount(totalAmount);
        order.setUser(user);
        order.setOrderDate(new Date());
        List<orderItem> orderItems = new ArrayList<>();
        List<cartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new RuntimeException("cart is empty");
        }
        // TODO: SEARCH N+1 QUERY
        for (cartItem item : cartItems) {
            orderItem orderItem = new orderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setProduct(item.getProduct());
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        orderRepository.save(order);
        cartService.deleteItems(cart.getId());
        cart.getCartItems().clear();

    }


}
