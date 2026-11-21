
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.BookingDTO;
import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    // CREATE: Customer or Admin
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<CommonResponseDto<BookingDTO>> createBooking(
            @Valid @RequestBody BookingDTO bookingDTO) {

        log.info("Booking creation request received for customer ID: {}",
                bookingDTO.getCustomerId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(bookingDTO));
    }

    // READ ALL: Admin only
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<List<BookingDTO>>> getAllBookings() {

        log.info("Request received to retrieve all bookings");

        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // READ BY ID: Customer or Admin
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<CommonResponseDto<BookingDTO>> getBookingById(
            @PathVariable Integer id) {

        log.info("Request received to retrieve booking ID: {}", id);

        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // UPDATE: Customer or Admin
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<CommonResponseDto<BookingDTO>> updateBooking(
            @PathVariable Integer id,
            @Valid @RequestBody BookingDTO bookingDTO) {

        log.info("Booking update request received. Booking ID: {}", id);

        return ResponseEntity.ok(
                bookingService.updateBooking(id, bookingDTO)
        );
    }

    // DELETE: Admin only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<Void>> deleteBooking(
            @PathVariable Integer id) {

        log.info("Booking deletion request received. Booking ID: {}", id);

        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }
}
