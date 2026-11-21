
package com.cityride.cabservice.dto;

import com.cityride.cabservice.enums.PaymentMethod;
import com.cityride.cabservice.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDTO {

    private Integer paymentId;
    private Integer bookingId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
    private String transactionReference;
    private LocalDateTime createdAt;
}
