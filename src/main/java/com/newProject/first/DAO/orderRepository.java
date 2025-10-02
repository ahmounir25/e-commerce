package com.newProject.first.DAO;

import com.newProject.first.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface orderRepository extends JpaRepository<Order,Integer> {

    Optional<List<Order>> findByUserId(int id);

    @Query("Select o From Order o Join Fetch o.orderItems oi Join Fetch oi.product p Where o.user.id= :userId")
    Page<Order> findOrdersWithItemsAndProducts(@Param("userId") int userId, Pageable pageable);
}
