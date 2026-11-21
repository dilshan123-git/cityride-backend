package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.DriverDTO;

import java.util.List;

public interface DriverService {

    DriverDTO createDriver(DriverDTO driverDTO);

    List<DriverDTO> getAllDrivers();

    DriverDTO getDriverById(Integer id);

    DriverDTO updateDriver(Integer id, DriverDTO driverDTO);

    void deleteDriver(Integer id);
}