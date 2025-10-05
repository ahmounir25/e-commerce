package com.newProject.first.DAO;

import com.newProject.first.entity.RefreshToken;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface refreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
    void deleteByEmail(String email);

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken ex WHERE ex.expiryDate <= :currentDate")
    void deleteExpiredTokens(@Param("currentDate")Date currentDate);
}
