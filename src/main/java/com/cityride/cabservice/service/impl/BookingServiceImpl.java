package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.BookingDTO;
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

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    // CREATE BOOKING
    @Override
    public BookingDTO createBooking(BookingDTO dto) {

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"
                ));

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

        return BookingMapper.convertToDTO(bookingRepository.save(booking));
    }

    // GET ALL BOOKINGS
    @Override
    @Transactional(readOnly = true)
    public List<BookingDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(BookingMapper::convertToDTO)
                .toList();
    }

    // GET BOOKING BY ID
    @Override
    @Transactional(readOnly = true)
    public BookingDTO getBookingById(Integer id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking not found"
                ));

        return BookingMapper.convertToDTO(booking);
    }

    // UPDATE BOOKING
    @Override
    public BookingDTO updateBooking(Integer id, BookingDTO dto) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking not found"
                ));

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"
                ));

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

        return BookingMapper.convertToDTO(bookingRepository.save(booking));
    }

    // DELETE BOOKING
    @Override
    public void deleteBooking(Integer id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking not found"
                ));

        bookingRepository.delete(booking);
    }

    // GET DRIVER
    private Driver getDriver(Integer driverId) {

        if (driverId == null) {
            return null;
        }

        return driverRepository.findById(driverId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Driver not found"
                ));
    }

    // GET VEHICLE
    private Vehicle getVehicle(Integer vehicleId) {

        if (vehicleId == null) {
            return null;
        }

        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Vehicle not found"
                ));
    }
}