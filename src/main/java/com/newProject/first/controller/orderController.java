package com.newProject.first.controller;

import com.newProject.first.DTO.ordersResponse;
import com.newProject.first.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class orderController {

    private orderService orderService;
    private JwtService jwtService;
    private userService userService;
    private cartService cartService;
    private productService productService;

    @Autowired
    public orderController(orderService orderService,
                           JwtService jwtService,
                           userService userService,
                           cartService cartService,
                           productService productService){
        this.orderService=orderService;
        this.jwtService=jwtService;
        this.userService=userService;
        this.cartService=cartService;
        this.productService=productService;
    }
    @GetMapping("/orders")
    public ResponseEntity<Page<ordersResponse>> getOrders(@RequestHeader(name = "Authorization") String authHeader,
                                                          @PageableDefault(size = 5) Pageable pageable){
        if (authHeader==null||!authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email=jwtService.getEmail(authHeader);
        Page<ordersResponse> orders=orderService.findOrders(email,pageable);

        return ResponseEntity.ok().body(orders);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> addOrder(@RequestHeader(name = "Authorization") String authHeader){
        if (authHeader==null||!authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid auth header");
        }
        String email=jwtService.getEmail(authHeader);
        orderService.save(email);
        return  ResponseEntity.ok().body("order has been placed");
    }

}
