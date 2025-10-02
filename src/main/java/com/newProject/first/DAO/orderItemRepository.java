package com.newProject.first.DAO;

import com.newProject.first.entity.orderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderItemRepository extends JpaRepository<orderItem,Integer> {

}
