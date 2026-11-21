package com.cityride.cabservice.repository;

import com.cityride.cabservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmailAndUserIdNot(String email, Integer userId);

    boolean existsByPhoneAndUserIdNot(String phone, Integer userId);
}
