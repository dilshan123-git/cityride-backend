package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.BookingDTO;
import com.cityride.cabservice.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // CREATE: any logged-in customer or admin
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<BookingDTO> createBooking(
            @Valid @RequestBody BookingDTO bookingDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(bookingDTO));
    }

    // READ ALL: admin only
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {

        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // READ BY ID: customer or admin
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // UPDATE: customer or admin
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<BookingDTO> updateBooking(
            @PathVariable Integer id,
            @Valid @RequestBody BookingDTO bookingDTO) {

        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    // DELETE: admin only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBooking(
            @PathVariable Integer id) {

        bookingService.deleteBooking(id);

        return ResponseEntity.ok("Booking deleted successfully");
    }
}