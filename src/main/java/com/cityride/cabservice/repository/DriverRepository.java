package com.cityride.cabservice.repository;

import com.cityride.cabservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByPhoneAndUserIdNot(String phone, Integer userId);


    boolean existsByEmailAndUserIdNot(String email, Integer userId);

    boolean existsByLicenseNumberAndUserIdNot(
            String licenseNumber, Integer userId);
}
