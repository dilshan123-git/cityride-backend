
package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {

    CommonResponseDto<PaymentDTO> createPayment(PaymentDTO paymentDTO);

    CommonResponseDto<List<PaymentDTO>> getAllPayments();

    CommonResponseDto<PaymentDTO> getPaymentById(Integer id);

    CommonResponseDto<PaymentDTO> updatePayment(
            Integer id, PaymentDTO paymentDTO);

    CommonResponseDto<Void> deletePayment(Integer id);
}
