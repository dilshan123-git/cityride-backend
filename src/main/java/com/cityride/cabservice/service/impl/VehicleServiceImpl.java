
package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.VehicleDTO;
import com.cityride.cabservice.entity.Vehicle;
import com.cityride.cabservice.enums.VehicleStatus;
import com.cityride.cabservice.mapper.VehicleMapper;
import com.cityride.cabservice.repository.VehicleRepository;
import com.cityride.cabservice.service.VehicleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    // CREATE VEHICLE
    @Override
    public CommonResponseDto<VehicleDTO> createVehicle(VehicleDTO dto) {

        log.info("Creating vehicle. Vehicle number: {}", dto.getVehicleNumber());

        String vehicleNumber = dto.getVehicleNumber().trim().toUpperCase();

        if (vehicleRepository.existsByVehicleNumber(vehicleNumber)) {
            log.warn("Vehicle number already exists: {}", vehicleNumber);

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vehicle number already exists"
            );
        }

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());

        vehicle.setManufactureYear(
                dto.getManufactureYear() != null
                        ? Year.of(dto.getManufactureYear())
                        : null
        );

        vehicle.setSeatingCapacity(dto.getSeatingCapacity());

        vehicle.setStatus(dto.getStatus() != null
                ? dto.getStatus()
                : VehicleStatus.Available);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        log.info("Vehicle created successfully. Vehicle ID: {}",
                savedVehicle.getVehicleId());

        return CommonResponseDto.success(
                "Vehicle created successfully",
                VehicleMapper.convertToDTO(savedVehicle)
        );
    }

    // GET ALL VEHICLES
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<List<VehicleDTO>> getAllVehicles() {

        log.info("Retrieving all vehicles");

        List<VehicleDTO> vehicles = vehicleRepository.findAll()
                .stream()
                .map(VehicleMapper::convertToDTO)
                .toList();

        log.info("Retrieved {} vehicles successfully", vehicles.size());

        return CommonResponseDto.success(
                "Vehicles retrieved successfully",
                vehicles
        );
    }

    // GET VEHICLE BY ID
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<VehicleDTO> getVehicleById(Integer id) {

        log.info("Retrieving vehicle ID: {}", id);

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Vehicle not found. Vehicle ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Vehicle not found"
                    );
                });

        return CommonResponseDto.success(
                "Vehicle retrieved successfully",
                VehicleMapper.convertToDTO(vehicle)
        );
    }

    // UPDATE VEHICLE
    @Override
    public CommonResponseDto<VehicleDTO> updateVehicle(
            Integer id, VehicleDTO dto) {

        log.info("Updating vehicle ID: {}", id);

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Vehicle not found for update. ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Vehicle not found"
                    );
                });

        String vehicleNumber = dto.getVehicleNumber().trim().toUpperCase();

        if (vehicleRepository.existsByVehicleNumberAndVehicleIdNot(
                vehicleNumber, id)) {

            log.warn("Vehicle number already belongs to another vehicle: {}",
                    vehicleNumber);

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vehicle number already exists"
            );
        }

        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());

        vehicle.setManufactureYear(
                dto.getManufactureYear() != null
                        ? Year.of(dto.getManufactureYear())
                        : null
        );

        vehicle.setSeatingCapacity(dto.getSeatingCapacity());

        if (dto.getStatus() != null) {
            vehicle.setStatus(dto.getStatus());
        }

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        log.info("Vehicle updated successfully. Vehicle ID: {}", id);

        return CommonResponseDto.success(
                "Vehicle updated successfully",
                VehicleMapper.convertToDTO(updatedVehicle)
        );
    }

    // DELETE VEHICLE
    @Override
    public CommonResponseDto<Void> deleteVehicle(Integer id) {

        log.info("Deleting vehicle ID: {}", id);

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Vehicle not found for deletion. ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Vehicle not found"
                    );
                });

        vehicleRepository.delete(vehicle);

        log.info("Vehicle deleted successfully. Vehicle ID: {}", id);

        return CommonResponseDto.success(
                "Vehicle deleted successfully",
                null
        );
    }
}
