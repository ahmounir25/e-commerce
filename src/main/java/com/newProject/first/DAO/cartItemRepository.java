package com.newProject.first.DAO;

import com.newProject.first.entity.cartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface cartItemRepository extends JpaRepository<cartItem, Integer> {


    void deleteAllByCartId(int cartId);

    // solve N+1 query by @EntityGraph (like Join Fetch)
    @EntityGraph(attributePaths = {"product"})
    Page<cartItem> findByCartId(@Param("cartId") int cartId, Pageable pageable);
}
