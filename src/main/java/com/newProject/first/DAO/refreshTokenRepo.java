package com.newProject.first.DAO;

import com.newProject.first.entity.RefreshToken;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface refreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
     void deleteByEmail(String email);
     Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken>findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByToken(String token);
}
