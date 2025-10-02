package com.newProject.first.DAO;

import com.newProject.first.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface roleRepository extends JpaRepository<Role,String> {
     Optional<Role> findByRole(String role);
}
