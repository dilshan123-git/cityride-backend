
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.PaymentDTO;
import com.cityride.cabservice.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    // CREATE PAYMENT
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<CommonResponseDto<PaymentDTO>> createPayment(
            @Valid @RequestBody PaymentDTO paymentDTO) {

        log.info("REST request to create payment. Booking ID: {}",
                paymentDTO.getBookingId());

        CommonResponseDto<PaymentDTO> response =
                paymentService.createPayment(paymentDTO);

        log.info("Payment created successfully. Payment ID: {}",
                response.getData() != null
                        ? response.getData().getPaymentId()
                        : null);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL PAYMENTS
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<List<PaymentDTO>>> getAllPayments() {

        log.info("REST request to retrieve all payments");

        CommonResponseDto<List<PaymentDTO>> response =
                paymentService.getAllPayments();

        log.info("Successfully retrieved all payments");

        return ResponseEntity.ok(response);
    }

    // GET PAYMENT BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DRIVER', 'ADMIN')")
    public ResponseEntity<CommonResponseDto<PaymentDTO>> getPaymentById(
            @PathVariable Integer id) {

        log.info("REST request to retrieve payment. ID: {}", id);

        CommonResponseDto<PaymentDTO> response =
                paymentService.getPaymentById(id);

        log.info("Payment retrieved successfully. ID: {}", id);

        return ResponseEntity.ok(response);
    }

    // UPDATE PAYMENT
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<PaymentDTO>> updatePayment(
            @PathVariable Integer id,
            @Valid @RequestBody PaymentDTO paymentDTO) {

        log.info("REST request to update payment. ID: {}", id);

        CommonResponseDto<PaymentDTO> response =
                paymentService.updatePayment(id, paymentDTO);

        log.info("Payment updated successfully. ID: {}", id);

        return ResponseEntity.ok(response);
    }

    // DELETE PAYMENT
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<Void>> deletePayment(
            @PathVariable Integer id) {

        log.info("REST request to delete payment. ID: {}", id);

        CommonResponseDto<Void> response =
                paymentService.deletePayment(id);

        log.info("Payment deleted successfully. ID: {}", id);

        return ResponseEntity.ok(response);
    }
}
