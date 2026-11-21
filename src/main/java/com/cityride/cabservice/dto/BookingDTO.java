package com.cityride.cabservice.dto;

import com.cityride.cabservice.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {

    private Integer bookingId;
    private Integer customerId;
    private Integer driverId;
    private Integer vehicleId;
    private String pickupLocation;
    private String destination;
    private LocalDateTime pickupDate;
    private Integer passengerCount;
    private BigDecimal distanceKm;
    private BigDecimal fareAmount;
    private BookingStatus bookingStatus;
    private LocalDateTime bookingDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}