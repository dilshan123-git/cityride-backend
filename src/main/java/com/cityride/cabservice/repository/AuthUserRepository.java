package com.cityride.cabservice.repository;

import com.cityride.cabservice.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    boolean existsByEmail(String email);
    Optional<AuthUser> findByEmail(String email);
    boolean existsByEmailAndUserIdNot(String email, Integer userId);
}
