package com.newProject.first.DAO;

import com.newProject.first.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
