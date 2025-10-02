package com.newProject.first.service;

import com.newProject.first.DAO.cartItemRepository;
import com.newProject.first.DAO.cartRepository;
import com.newProject.first.DAO.productRepository;
import com.newProject.first.DTO.cartResponse;
import com.newProject.first.DTO.itemResponse;
import com.newProject.first.entity.Cart;
import com.newProject.first.entity.Product;
import com.newProject.first.entity.User;
import com.newProject.first.entity.cartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class cartServiceImp implements cartService {

    private userService userService;
    private cartRepository cartRepository;
    private productRepository productRepository;
    private cartItemRepository cartItemRepository;

    @Autowired
    public cartServiceImp(userService userService,
                          cartRepository cartRepository,
                          productRepository productRepository,
                          cartItemRepository cartItemRepository) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart addToCart(String email, int product_id, int quantity) {
        User user = userService.findUserByEmail(email);

        Cart cart = cartRepository.findByUser_id(user.getId()).orElseGet(
                () -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    // save it to DB
                    cartRepository.save(newCart);
                    return newCart;
                }
        );
        Product product = productRepository.findById(product_id).orElseThrow(
                () -> new RuntimeException("product not found")
        );

        cartItem cartItem = new cartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);

        // cause it's bi-directional and to make cascading work correctly
        cart.getCartItems().add(cartItem);

        return cartRepository.save(cart);

    }

    @Override
    public cartResponse findCart(String email) {
        User user = userService.findUserByEmail(email);
        Cart cart = cartRepository.findByUser_id(user.getId()).orElseThrow();
        double total = cart.getCartItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

        return new cartResponse(cart.getId(), user.getEmail(), total);
    }

    @Override
    public Page<itemResponse> findItems(String email, Pageable pageable) {
        User user = userService.findUserByEmail(email);
        Page<cartItem> items = cartItemRepository.
                findByCartId(user.getCart().getId(), pageable);
        // map to DTO
        return items.map(item->
                new itemResponse(
                        item.getId(),
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getProduct().getId()
                        )
                );
    }

    @Override
    public void deleteItem(int id, String email) {
        if (!cartItemRepository.existsById(id)) {
            throw new RuntimeException("not found");
        }
        User user = userService.findUserByEmail(email);
        cartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        if (!(user.getCart().getId() == cartItem.getCart().getId())) {
            throw new RuntimeException("UnAuthorized");
        } else
            cartItemRepository.deleteById(id);
    }

    @Override
    public itemResponse getItem(int id, String email) {
        cartItem item = cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        User user = userService.findUserByEmail(email);

        if (user.getId() == item.getCart().getUser().getId()) {
            itemResponse response = new itemResponse();
            response.setId(item.getId());
            response.setItemName(item.getProduct().getName());
            response.setPrice(item.getPrice());
            response.setQuantity(item.getQuantity());
            return response;
        } else {
            throw new RuntimeException("UnAuthorized");
        }
    }

    @Override
    public itemResponse updateItemQuantity(int id, String email, int quantity) {
        cartItem item = cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        User user = userService.findUserByEmail(email);

        if (user.getId() == item.getCart().getUser().getId()) {
            item.setQuantity(quantity);
            itemResponse response = new itemResponse();
            response.setId(item.getId());
            response.setProductId(item.getProduct().getId());
            response.setItemName(item.getProduct().getName());
            response.setPrice(item.getPrice());
            response.setQuantity(item.getQuantity());
            cartItemRepository.save(item);

            return response;
        } else {
            throw new RuntimeException("UnAuthorized");
        }
    }

    @Override
    public void deleteItems(int cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
    }

    @Override
    public Cart getCartWithItemsAndProducts(int id) {
        Cart cart = cartRepository.findCartWithCartItemsAndProducts(id).orElseThrow(() -> new RuntimeException("Not found"));
        return cart;
    }
}
