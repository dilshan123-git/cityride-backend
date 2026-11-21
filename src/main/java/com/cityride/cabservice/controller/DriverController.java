
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.DriverDTO;
import com.cityride.cabservice.service.DriverService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<DriverDTO>> createDriver(
            @RequestBody DriverDTO driverDTO) {

        log.info("REST request to create driver. Email: {}", driverDTO.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverService.createDriver(driverDTO));
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto<List<DriverDTO>>> getAllDrivers() {

        log.info("REST request to retrieve all drivers");

        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto<DriverDTO>> getDriverById(
            @PathVariable Integer id) {

        log.info("REST request to retrieve driver. ID: {}", id);

        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponseDto<DriverDTO>> updateDriver(
            @PathVariable Integer id,
            @RequestBody DriverDTO driverDTO) {

        log.info("REST request to update driver. ID: {}", id);

        return ResponseEntity.ok(driverService.updateDriver(id, driverDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponseDto<Void>> deleteDriver(
            @PathVariable Integer id) {

        log.info("REST request to delete driver. ID: {}", id);

        return ResponseEntity.ok(driverService.deleteDriver(id));
    }
}
