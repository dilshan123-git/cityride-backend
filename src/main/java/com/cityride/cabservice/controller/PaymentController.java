package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.PaymentDTO;
import com.cityride.cabservice.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<PaymentDTO> createPayment(
            @Valid @RequestBody PaymentDTO paymentDTO) {

        return new ResponseEntity<>(
                paymentService.createPayment(paymentDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {

        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','DRIVER','ADMIN')")
    public ResponseEntity<PaymentDTO> getPaymentById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentDTO> updatePayment(
            @PathVariable Integer id,
            @Valid @RequestBody PaymentDTO paymentDTO) {

        return ResponseEntity.ok(paymentService.updatePayment(id, paymentDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Integer id) {

        paymentService.deletePayment(id);

        return ResponseEntity.noContent().build();
    }
}