package com.cityride.cabservice.mapper;

import com.cityride.cabservice.dto.DriverDTO;
import com.cityride.cabservice.entity.Driver;

public class DriverMapping {

    public static DriverDTO convertToDTO(Driver driver) {

        DriverDTO dto = new DriverDTO();

        dto.setDriverId(driver.getUserId());
        dto.setFullName(driver.getFullName());
        dto.setPhone(driver.getPhone());
        dto.setEmail(driver.getEmail());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setAddress(driver.getAddress());
        dto.setAvailability(driver.getAvailability());

        return dto;
    }

}
