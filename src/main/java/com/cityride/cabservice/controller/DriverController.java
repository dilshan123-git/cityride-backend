package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.DriverDTO;
import com.cityride.cabservice.service.DriverService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverDTO> createDriver(
            @RequestBody DriverDTO driverDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverService.createDriver(driverDTO));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {

        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriverById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(
            @PathVariable Integer id,
            @RequestBody DriverDTO driverDTO) {

        return ResponseEntity.ok(
                driverService.updateDriver(id, driverDTO));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriver(
            @PathVariable Integer id) {

        driverService.deleteDriver(id);

        return ResponseEntity.ok("Driver deleted successfully");
    }
}