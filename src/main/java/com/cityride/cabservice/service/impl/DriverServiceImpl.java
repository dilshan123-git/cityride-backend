package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.DriverDTO;
import com.cityride.cabservice.entity.Driver;
import com.cityride.cabservice.enums.Availability;
import com.cityride.cabservice.mapper.DriverMapping;
import com.cityride.cabservice.repository.DriverRepository;
import com.cityride.cabservice.service.DriverService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    // CREATE DRIVER
    @Override
    public DriverDTO createDriver(DriverDTO dto) {

        if (driverRepository.existsByPhone(dto.getPhone())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Phone number already exists");
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()
                && driverRepository.existsByEmail(dto.getEmail())) {
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
        driver.setEmail(dto.getEmail());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setAddress(dto.getAddress());

        driver.setAvailability(dto.getAvailability() != null
                ? dto.getAvailability()
                : Availability.Available);

        return DriverMapping.convertToDTO(driverRepository.save(driver));
    }

    // GET ALL DRIVERS
    @Override
    @Transactional(readOnly = true)
    public List<DriverDTO> getAllDrivers() {

        return driverRepository.findAll()
                .stream()
                .map(DriverMapping::convertToDTO)
                .toList();
    }

    // GET DRIVER BY ID
    @Override
    @Transactional(readOnly = true)
    public DriverDTO getDriverById(Integer id) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Driver not found"));

        return DriverMapping.convertToDTO(driver);
    }

    // UPDATE DRIVER
    @Override
    public DriverDTO updateDriver(Integer id, DriverDTO dto) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Driver not found"));

        if (driverRepository.existsByPhoneAndUserIdNot(
                dto.getPhone(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Phone number already exists");
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()
                && driverRepository.existsByEmailAndUserIdNot(
                dto.getEmail(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists");
        }

        if (driverRepository.existsByLicenseNumberAndUserIdNot(
                dto.getLicenseNumber(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "License number already exists");
        }

        driver.setFullName(dto.getFullName());
        driver.setPhone(dto.getPhone());
        driver.setEmail(dto.getEmail());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setAddress(dto.getAddress());

        if (dto.getAvailability() != null) {
            driver.setAvailability(dto.getAvailability());
        }

        return DriverMapping.convertToDTO(driverRepository.save(driver));
    }

    // DELETE DRIVER
    @Override
    public void deleteDriver(Integer id) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Driver not found"));

        driverRepository.delete(driver);
    }
}