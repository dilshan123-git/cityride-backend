
package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.BookingDTO;
import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.entity.Booking;
import com.cityride.cabservice.entity.Customer;
import com.cityride.cabservice.entity.Driver;
import com.cityride.cabservice.entity.Vehicle;
import com.cityride.cabservice.enums.BookingStatus;
import com.cityride.cabservice.mapper.BookingMapper;
import com.cityride.cabservice.repository.BookingRepository;
import com.cityride.cabservice.repository.CustomerRepository;
import com.cityride.cabservice.repository.DriverRepository;
import com.cityride.cabservice.repository.VehicleRepository;
import com.cityride.cabservice.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    // CREATE BOOKING
    @Override
    public CommonResponseDto<BookingDTO> createBooking(BookingDTO dto) {

        log.info("Creating booking for customer ID: {}", dto.getCustomerId());

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> {
                    log.warn("Customer not found. Customer ID: {}", dto.getCustomerId());
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Customer not found"
                    );
                });

        Booking booking = new Booking();

        booking.setCustomer(customer);
        booking.setDriver(getDriver(dto.getDriverId()));
        booking.setVehicle(getVehicle(dto.getVehicleId()));

        booking.setPickupLocation(dto.getPickupLocation());
        booking.setDestination(dto.getDestination());
        booking.setPickupDate(dto.getPickupDate());
        booking.setPassengerCount(dto.getPassengerCount());
        booking.setDistanceKm(dto.getDistanceKm());
        booking.setFareAmount(dto.getFareAmount());

        booking.setBookingStatus(dto.getBookingStatus() != null
                ? dto.getBookingStatus()
                : BookingStatus.Pending);

        Booking savedBooking = bookingRepository.save(booking);
        BookingDTO response = BookingMapper.convertToDTO(savedBooking);

        log.info("Booking created successfully. Booking ID: {}",
                savedBooking.getBookingId());

        return CommonResponseDto.success(
                "Booking created successfully",
                response
        );
    }

    // GET ALL BOOKINGS
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<List<BookingDTO>> getAllBookings() {

        log.info("Retrieving all bookings");

        List<BookingDTO> bookings = bookingRepository.findAll()
                .stream()
                .map(BookingMapper::convertToDTO)
                .toList();

        log.info("Retrieved {} bookings successfully", bookings.size());

        return CommonResponseDto.success(
                "Bookings retrieved successfully",
                bookings
        );
    }

    // GET BOOKING BY ID
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<BookingDTO> getBookingById(Integer id) {

        log.info("Retrieving booking ID: {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Booking not found. Booking ID: {}", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Booking not found"
                    );
                });

        BookingDTO response = BookingMapper.convertToDTO(booking);

        log.info("Booking retrieved successfully. Booking ID: {}", id);

        return CommonResponseDto.success(
                "Booking retrieved successfully",
                response
        );
    }

    // UPDATE BOOKING
    @Override
    public CommonResponseDto<BookingDTO> updateBooking(Integer id, BookingDTO dto) {

        log.info("Updating booking ID: {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Booking not found for update. Booking ID: {}", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Booking not found"
                    );
                });

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> {
                    log.warn("Customer not found. Customer ID: {}", dto.getCustomerId());
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Customer not found"
                    );
                });

        booking.setCustomer(customer);
        booking.setDriver(getDriver(dto.getDriverId()));
        booking.setVehicle(getVehicle(dto.getVehicleId()));

        booking.setPickupLocation(dto.getPickupLocation());
        booking.setDestination(dto.getDestination());
        booking.setPickupDate(dto.getPickupDate());
        booking.setPassengerCount(dto.getPassengerCount());
        booking.setDistanceKm(dto.getDistanceKm());
        booking.setFareAmount(dto.getFareAmount());

        if (dto.getBookingStatus() != null) {
            booking.setBookingStatus(dto.getBookingStatus());
        }

        Booking updatedBooking = bookingRepository.save(booking);
        BookingDTO response = BookingMapper.convertToDTO(updatedBooking);

        log.info("Booking updated successfully. Booking ID: {}", id);

        return CommonResponseDto.success(
                "Booking updated successfully",
                response
        );
    }

    // DELETE BOOKING
    @Override
    public CommonResponseDto<Void> deleteBooking(Integer id) {

        log.info("Deleting booking ID: {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Booking not found for deletion. Booking ID: {}", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Booking not found"
                    );
                });

        bookingRepository.delete(booking);

        log.info("Booking deleted successfully. Booking ID: {}", id);

        return CommonResponseDto.success(
                "Booking deleted successfully",
                null
        );
    }

    // GET DRIVER
    private Driver getDriver(Integer driverId) {

        if (driverId == null) {
            return null;
        }

        return driverRepository.findById(driverId)
                .orElseThrow(() -> {
                    log.warn("Driver not found. Driver ID: {}", driverId);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Driver not found"
                    );
                });
    }

    // GET VEHICLE
    private Vehicle getVehicle(Integer vehicleId) {

        if (vehicleId == null) {
            return null;
        }

        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> {
                    log.warn("Vehicle not found. Vehicle ID: {}", vehicleId);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Vehicle not found"
                    );
                });
    }
}
