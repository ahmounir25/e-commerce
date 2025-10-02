package com.newProject.first.DAO;

import com.newProject.first.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface cartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser_id(int id);
    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE c.id = :cartId")
    Optional<Cart> findCartWithCartItemsAndProducts(@Param("cartId") int cartId);
}
