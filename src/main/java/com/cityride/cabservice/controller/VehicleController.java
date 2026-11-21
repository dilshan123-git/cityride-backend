
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.VehicleDTO;
import com.cityride.cabservice.service.VehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    private final VehicleService vehicleService;

    // CREATE: Admin only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<VehicleDTO>> createVehicle(
            @Valid @RequestBody VehicleDTO vehicleDTO) {

        log.info("Vehicle creation request received");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleService.createVehicle(vehicleDTO));
    }

    // READ ALL: Admin, Customer or Driver
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','DRIVER')")
    public ResponseEntity<CommonResponseDto<List<VehicleDTO>>> getAllVehicles() {

        log.info("Request received to retrieve all vehicles");

        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    // READ BY ID: Admin, Customer or Driver
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','DRIVER')")
    public ResponseEntity<CommonResponseDto<VehicleDTO>> getVehicleById(
            @PathVariable Integer id) {

        log.info("Request received to retrieve vehicle ID: {}", id);

        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    // UPDATE: Admin only
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<VehicleDTO>> updateVehicle(
            @PathVariable Integer id,
            @Valid @RequestBody VehicleDTO vehicleDTO) {

        log.info("Vehicle update request received. Vehicle ID: {}", id);

        return ResponseEntity.ok(
                vehicleService.updateVehicle(id, vehicleDTO)
        );
    }

    // DELETE: Admin only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<Void>> deleteVehicle(
            @PathVariable Integer id) {

        log.info("Vehicle deletion request received. Vehicle ID: {}", id);

        return ResponseEntity.ok(vehicleService.deleteVehicle(id));
    }
}
