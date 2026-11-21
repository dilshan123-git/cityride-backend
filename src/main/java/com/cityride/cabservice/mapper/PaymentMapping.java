package com.cityride.cabservice.mapper;

import com.cityride.cabservice.dto.PaymentDTO;
import com.cityride.cabservice.entity.Payment;

public class PaymentMapping {

    public static PaymentDTO convertToDTO(Payment payment) {

        PaymentDTO dto = new PaymentDTO();

        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBooking().getBookingId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setTransactionReference(payment.getTransactionReference());
        dto.setCreatedAt(payment.getCreatedAt());

        return dto;
    }
}
