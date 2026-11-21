package com.cityride.cabservice.mapper;

import com.cityride.cabservice.dto.BookingDTO;
import com.cityride.cabservice.entity.Booking;

public class BookingMapper {

    public static BookingDTO convertToDTO(Booking booking) {

        BookingDTO dto = new BookingDTO();

        dto.setBookingId(booking.getBookingId());
        dto.setCustomerId(booking.getCustomer().getUserId());

        dto.setDriverId(
                booking.getDriver() != null
                        ? booking.getDriver().getUserId()
                        : null
        );

        dto.setVehicleId(
                booking.getVehicle() != null
                        ? booking.getVehicle().getVehicleId()
                        : null
        );

        dto.setPickupLocation(booking.getPickupLocation());
        dto.setDestination(booking.getDestination());
        dto.setBookingDate(booking.getBookingDate());
        dto.setPickupDate(booking.getPickupDate());
        dto.setPassengerCount(booking.getPassengerCount());
        dto.setDistanceKm(booking.getDistanceKm());
        dto.setFareAmount(booking.getFareAmount());
        dto.setBookingStatus(booking.getBookingStatus());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());

        return dto;
    }
}
