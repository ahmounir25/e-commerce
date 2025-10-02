package com.newProject.first.controller;

import com.newProject.first.DTO.cartResponse;
import com.newProject.first.DTO.addToCartRequest;
import com.newProject.first.DTO.itemResponse;
import com.newProject.first.entity.Cart;
import com.newProject.first.entity.cartItem;
import com.newProject.first.service.JwtService;
import com.newProject.first.service.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class cartController {

    private cartService cartService;
    private JwtService jwtService;

    @Autowired
    public cartController(cartService cartService, JwtService jwtService) {
        this.cartService = cartService;
        this.jwtService = jwtService;
    }

    @PostMapping("/cart/items")
    public ResponseEntity<?> addToCart(@RequestHeader(name = "Authorization") String authHeader,
                                       @RequestBody addToCartRequest request) {

        String email = jwtService.getEmail(authHeader);

        Cart updatedCart = cartService.addToCart(email, request.getProduct_id(), request.getQuantity());

        return ResponseEntity.ok("Added Successfully");
    }

    //TODO: RETURN CART INFO WITHOUT ITEMS AND ADD ENDPOINT /CART/ITEMS TO RETURN ITEMS //Done
    @GetMapping("/cart")
    public ResponseEntity<cartResponse> getCart(@RequestHeader(name = "Authorization") String authHeader){
        String email = jwtService.getEmail(authHeader);
        cartResponse response=cartService.findCart(email);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cart/items")
    public ResponseEntity<Page<itemResponse>> getCartItems(@RequestHeader(name = "Authorization") String authHeader,
                                                       @PageableDefault(size = 5) Pageable pageable) {
        String email = jwtService.getEmail(authHeader);
        Page<itemResponse> pages=cartService.findItems(email,pageable);

        return ResponseEntity.ok(pages);
    }


    @GetMapping("/cart/items/{id}")
    public ResponseEntity<itemResponse> getItem(@RequestHeader(name = "Authorization") String authHeader,
                                                @PathVariable int id){
        String email = jwtService.getEmail(authHeader);
        itemResponse response=cartService.getItem(id,email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @PatchMapping ("/cart/items/{id}")
    public ResponseEntity<itemResponse> updateItemQuantity(@RequestHeader(name = "Authorization") String authHeader,
                                                @PathVariable int id,@RequestBody int quantity){
        String email = jwtService.getEmail(authHeader);
        itemResponse response=cartService.updateItemQuantity(id,email,quantity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<?> deleteItem(@RequestHeader(name = "Authorization") String authHeader,
                           @PathVariable int id){
        String email = jwtService.getEmail(authHeader);
        cartService.deleteItem(id,email);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }
}
