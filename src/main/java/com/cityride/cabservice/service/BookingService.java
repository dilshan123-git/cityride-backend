package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.BookingDTO;
import com.cityride.cabservice.dto.CommonResponseDto;

import java.util.List;

public interface BookingService {

    CommonResponseDto<BookingDTO> createBooking(BookingDTO bookingDTO);

    CommonResponseDto<List<BookingDTO>> getAllBookings();

    CommonResponseDto<BookingDTO> getBookingById(Integer id);

    CommonResponseDto<BookingDTO> updateBooking(Integer id, BookingDTO bookingDTO);

    CommonResponseDto<Void> deleteBooking(Integer id);
}