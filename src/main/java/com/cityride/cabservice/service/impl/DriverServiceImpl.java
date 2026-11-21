
package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.DriverDTO;
import com.cityride.cabservice.entity.Driver;
import com.cityride.cabservice.enums.Availability;
import com.cityride.cabservice.enums.Roles;
import com.cityride.cabservice.enums.UserStatus;
import com.cityride.cabservice.mapper.DriverMapping;
import com.cityride.cabservice.repository.DriverRepository;
import com.cityride.cabservice.service.DriverService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE DRIVER
    @Override
    public CommonResponseDto<DriverDTO> createDriver(DriverDTO dto) {

        log.info("Creating driver. Phone: {}", dto.getPhone());

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email is required");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Password is required");
        }

        String email = dto.getEmail().trim().toLowerCase();

        if (driverRepository.existsByPhone(dto.getPhone())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Phone number already exists");
        }

        if (driverRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists");
        }

        if (driverRepository.existsByLicenseNumber(dto.getLicenseNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "License number already exists");
        }

        Driver driver = new Driver();

        driver.setFullName(dto.getFullName());
        driver.setPhone(dto.getPhone());
        driver.setEmail(email);
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setAddress(dto.getAddress());

        driver.setAvailability(dto.getAvailability() != null
                ? dto.getAvailability()
                : Availability.Available);

        driver.setPassword(passwordEncoder.encode(dto.getPassword()));
        driver.setRole(Roles.DRIVER);
        driver.setUserStatus(UserStatus.ACTIVE);

        Driver savedDriver = driverRepository.save(driver);

        log.info("Driver created successfully. ID: {}", savedDriver.getUserId());

        return CommonResponseDto.success(
                "Driver created successfully",
                DriverMapping.convertToDTO(savedDriver));
    }

    // GET ALL DRIVERS
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<List<DriverDTO>> getAllDrivers() {

        log.info("Fetching all drivers");

        List<DriverDTO> drivers = driverRepository.findAll()
                .stream()
                .map(DriverMapping::convertToDTO)
                .toList();

        log.info("Successfully fetched {} drivers", drivers.size());

        return CommonResponseDto.success(
                "Drivers retrieved successfully", drivers);
    }

    // GET DRIVER BY ID
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<DriverDTO> getDriverById(Integer id) {

        log.info("Fetching driver. ID: {}", id);

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Driver not found. ID: {}", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Driver not found");
                });

        log.info("Driver fetched successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Driver retrieved successfully",
                DriverMapping.convertToDTO(driver));
    }

    // UPDATE DRIVER
    @Override
    public CommonResponseDto<DriverDTO> updateDriver(Integer id, DriverDTO dto) {

        log.info("Updating driver. ID: {}", id);

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Driver not found for update. ID: {}", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Driver not found");
                });

        if (driverRepository.existsByPhoneAndUserIdNot(dto.getPhone(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Phone number already exists");
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {

            String email = dto.getEmail().trim().toLowerCase();

            if (driverRepository.existsByEmailAndUserIdNot(email, id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Email already exists");
            }

            driver.setEmail(email);
        }

        if (driverRepository.existsByLicenseNumberAndUserIdNot(
                dto.getLicenseNumber(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "License number already exists");
        }

        driver.setFullName(dto.getFullName());
        driver.setPhone(dto.getPhone());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setAddress(dto.getAddress());

        if (dto.getAvailability() != null) {
            driver.setAvailability(dto.getAvailability());
        }

        Driver updatedDriver = driverRepository.save(driver);

        log.info("Driver updated successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Driver updated successfully",
                DriverMapping.convertToDTO(updatedDriver));
    }

    // DELETE DRIVER
    @Override
    public CommonResponseDto<Void> deleteDriver(Integer id) {

        log.info("Deleting driver. ID: {}", id);

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Driver not found for deletion. ID: {}", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Driver not found");
                });

        driverRepository.delete(driver);

        log.info("Driver deleted successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Driver deleted successfully", null);
    }
}
