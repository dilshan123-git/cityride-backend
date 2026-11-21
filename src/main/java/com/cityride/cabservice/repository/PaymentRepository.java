package com.cityride.cabservice.repository;

import com.cityride.cabservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    boolean existsByBooking_BookingId(Integer bookingId);

    boolean existsByBooking_BookingIdAndPaymentIdNot(
            Integer bookingId, Integer paymentId
    );

    boolean existsByTransactionReference(String transactionReference);

    boolean existsByTransactionReferenceAndPaymentIdNot(
            String transactionReference, Integer paymentId
    );
}
