
package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.PaymentDTO;
import com.cityride.cabservice.entity.Booking;
import com.cityride.cabservice.entity.Payment;
import com.cityride.cabservice.enums.PaymentStatus;
import com.cityride.cabservice.mapper.PaymentMapping;
import com.cityride.cabservice.repository.BookingRepository;
import com.cityride.cabservice.repository.PaymentRepository;
import com.cityride.cabservice.service.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {

        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking not found"
                ));

        if (paymentRepository.existsByBooking_BookingId(
                paymentDTO.getBookingId())) {
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

        return PaymentMapping.convertToDTO(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapping::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Integer id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment not found"
                ));

        return PaymentMapping.convertToDTO(payment);
    }

    @Override
    public PaymentDTO updatePayment(Integer id, PaymentDTO paymentDTO) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment not found"
                ));

        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking not found"
                ));

        if (paymentRepository.existsByBooking_BookingIdAndPaymentIdNot(
                paymentDTO.getBookingId(), id)) {
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

        return PaymentMapping.convertToDTO(paymentRepository.save(payment));
    }

    @Override
    public void deletePayment(Integer id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment not found"
                ));

        paymentRepository.delete(payment);
    }

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
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Transaction reference already exists"
            );
        }
    }


}
