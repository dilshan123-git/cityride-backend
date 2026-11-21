
package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.PaymentDTO;
import com.cityride.cabservice.entity.Booking;
import com.cityride.cabservice.entity.Payment;
import com.cityride.cabservice.enums.PaymentStatus;
import com.cityride.cabservice.mapper.PaymentMapping;
import com.cityride.cabservice.repository.BookingRepository;
import com.cityride.cabservice.repository.PaymentRepository;
import com.cityride.cabservice.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    // CREATE PAYMENT
    @Override
    public CommonResponseDto<PaymentDTO> createPayment(PaymentDTO paymentDTO) {

        log.info("Creating payment for booking ID: {}", paymentDTO.getBookingId());

        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> {
                    log.warn("Booking not found. ID: {}", paymentDTO.getBookingId());

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Booking not found"
                    );
                });

        if (paymentRepository.existsByBooking_BookingId(
                paymentDTO.getBookingId())) {

            log.warn("Payment already exists for booking ID: {}",
                    paymentDTO.getBookingId());

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This booking already has a payment"
            );
        }

        checkTransactionReference(paymentDTO.getTransactionReference(), null);

        Payment payment = new Payment();

        payment.setBooking(booking);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());

        payment.setPaymentStatus(
                paymentDTO.getPaymentStatus() != null
                        ? paymentDTO.getPaymentStatus()
                        : PaymentStatus.Pending
        );

        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setTransactionReference(
                paymentDTO.getTransactionReference()
        );

        Payment savedPayment = paymentRepository.save(payment);

        log.info("Payment created successfully. Payment ID: {}",
                savedPayment.getPaymentId());

        return CommonResponseDto.success(
                "Payment created successfully",
                PaymentMapping.convertToDTO(savedPayment)
        );
    }

    // GET ALL PAYMENTS
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<List<PaymentDTO>> getAllPayments() {

        log.info("Fetching all payments");

        List<PaymentDTO> payments = paymentRepository.findAll()
                .stream()
                .map(PaymentMapping::convertToDTO)
                .toList();

        log.info("Successfully fetched {} payments", payments.size());

        return CommonResponseDto.success(
                "Payments retrieved successfully",
                payments
        );
    }

    // GET PAYMENT BY ID
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<PaymentDTO> getPaymentById(Integer id) {

        log.info("Fetching payment. ID: {}", id);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Payment not found. ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Payment not found"
                    );
                });

        log.info("Payment fetched successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Payment retrieved successfully",
                PaymentMapping.convertToDTO(payment)
        );
    }

    // UPDATE PAYMENT
    @Override
    public CommonResponseDto<PaymentDTO> updatePayment(
            Integer id, PaymentDTO paymentDTO) {

        log.info("Updating payment. ID: {}", id);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Payment not found for update. ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Payment not found"
                    );
                });

        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> {
                    log.warn("Booking not found during payment update. ID: {}",
                            paymentDTO.getBookingId());

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Booking not found"
                    );
                });

        if (paymentRepository.existsByBooking_BookingIdAndPaymentIdNot(
                paymentDTO.getBookingId(), id)) {

            log.warn("Payment update failed. Booking ID {} already has another payment",
                    paymentDTO.getBookingId());

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This booking already has another payment"
            );
        }

        checkTransactionReference(
                paymentDTO.getTransactionReference(), id
        );

        payment.setBooking(booking);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());

        if (paymentDTO.getPaymentStatus() != null) {
            payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        }

        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setTransactionReference(
                paymentDTO.getTransactionReference()
        );

        Payment updatedPayment = paymentRepository.save(payment);

        log.info("Payment updated successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Payment updated successfully",
                PaymentMapping.convertToDTO(updatedPayment)
        );
    }

    // DELETE PAYMENT
    @Override
    public CommonResponseDto<Void> deletePayment(Integer id) {

        log.info("Deleting payment. ID: {}", id);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Payment not found for deletion. ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Payment not found"
                    );
                });

        paymentRepository.delete(payment);

        log.info("Payment deleted successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Payment deleted successfully",
                null
        );
    }

    // CHECK TRANSACTION REFERENCE
    private void checkTransactionReference(
            String reference, Integer paymentId) {

        if (reference == null || reference.isBlank()) {
            return;
        }

        boolean exists = paymentId == null
                ? paymentRepository.existsByTransactionReference(reference)
                : paymentRepository.existsByTransactionReferenceAndPaymentIdNot(
                reference, paymentId
        );

        if (exists) {
            log.warn("Transaction reference already exists: {}", reference);

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Transaction reference already exists"
            );
        }
    }
}
