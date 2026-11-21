
package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {

    PaymentDTO createPayment(PaymentDTO paymentDTO);

    List<PaymentDTO> getAllPayments();

    PaymentDTO getPaymentById(Integer id);

    PaymentDTO updatePayment(Integer id, PaymentDTO paymentDTO);

    void deletePayment(Integer id);
}
