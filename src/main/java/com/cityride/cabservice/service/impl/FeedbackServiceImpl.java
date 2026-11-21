package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.FeedbackDTO;
import com.cityride.cabservice.entity.Booking;
import com.cityride.cabservice.entity.Feedback;
import com.cityride.cabservice.mapper.FeedbackMapper;
import com.cityride.cabservice.repository.BookingRepository;
import com.cityride.cabservice.repository.FeedbackRepository;
import com.cityride.cabservice.service.FeedbackService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;

    @Override
    public FeedbackDTO createFeedback(FeedbackDTO dto) {

        if (feedbackRepository.existsByBooking_BookingId(dto.getBookingId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Feedback already exists for this booking"
            );
        }

        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Booking not found"
                ));

        Feedback feedback = new Feedback();
        feedback.setBooking(booking);
        feedback.setRating(dto.getRating());
        feedback.setComments(dto.getComments());

        return FeedbackMapper.convertToDTO(feedbackRepository.save(feedback));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDTO> getAllFeedback() {

        return feedbackRepository.findAll()
                .stream()
                .map(FeedbackMapper::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackDTO getFeedbackById(Integer id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Feedback not found"
                ));

        return FeedbackMapper.convertToDTO(feedback);
    }

    @Override
    public FeedbackDTO updateFeedback(Integer id, FeedbackDTO dto) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Feedback not found"
                ));

        if (!feedback.getBooking().getBookingId().equals(dto.getBookingId())) {

            if (feedbackRepository.existsByBooking_BookingId(dto.getBookingId())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Feedback already exists for this booking"
                );
            }

            Booking booking = bookingRepository.findById(dto.getBookingId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Booking not found"
                    ));

            feedback.setBooking(booking);
        }

        feedback.setRating(dto.getRating());
        feedback.setComments(dto.getComments());

        return FeedbackMapper.convertToDTO(feedbackRepository.save(feedback));
    }

    @Override
    public void deleteFeedback(Integer id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Feedback not found"
                ));

        feedbackRepository.delete(feedback);
    }
}