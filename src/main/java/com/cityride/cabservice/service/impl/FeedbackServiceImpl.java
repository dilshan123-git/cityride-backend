
package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.FeedbackDTO;
import com.cityride.cabservice.entity.Booking;
import com.cityride.cabservice.entity.Feedback;
import com.cityride.cabservice.mapper.FeedbackMapper;
import com.cityride.cabservice.repository.BookingRepository;
import com.cityride.cabservice.repository.FeedbackRepository;
import com.cityride.cabservice.service.FeedbackService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;

    // CREATE FEEDBACK
    @Override
    public CommonResponseDto<FeedbackDTO> createFeedback(FeedbackDTO dto) {

        log.info("Creating feedback for booking ID: {}", dto.getBookingId());

        if (feedbackRepository.existsByBooking_BookingId(dto.getBookingId())) {
            log.warn("Feedback already exists for booking ID: {}", dto.getBookingId());

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Feedback already exists for this booking"
            );
        }

        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> {
                    log.warn("Booking not found. ID: {}", dto.getBookingId());

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Booking not found"
                    );
                });

        Feedback feedback = new Feedback();
        feedback.setBooking(booking);
        feedback.setRating(dto.getRating());
        feedback.setComments(dto.getComments());

        Feedback savedFeedback = feedbackRepository.save(feedback);

        log.info("Feedback created successfully. Feedback ID: {}",
                savedFeedback.getFeedbackId());

        return CommonResponseDto.success(
                "Feedback created successfully",
                FeedbackMapper.convertToDTO(savedFeedback)
        );
    }

    // GET ALL FEEDBACK
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<List<FeedbackDTO>> getAllFeedback() {

        log.info("Fetching all feedback records");

        List<FeedbackDTO> feedbackList = feedbackRepository.findAll()
                .stream()
                .map(FeedbackMapper::convertToDTO)
                .toList();

        log.info("Successfully fetched {} feedback records", feedbackList.size());

        return CommonResponseDto.success(
                "Feedback retrieved successfully",
                feedbackList
        );
    }

    // GET FEEDBACK BY ID
    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<FeedbackDTO> getFeedbackById(Integer id) {

        log.info("Fetching feedback. ID: {}", id);

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Feedback not found. ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Feedback not found"
                    );
                });

        log.info("Feedback fetched successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Feedback retrieved successfully",
                FeedbackMapper.convertToDTO(feedback)
        );
    }

    // UPDATE FEEDBACK
    @Override
    public CommonResponseDto<FeedbackDTO> updateFeedback(Integer id, FeedbackDTO dto) {

        log.info("Updating feedback. ID: {}", id);

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Feedback not found for update. ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Feedback not found"
                    );
                });

        if (dto.getBookingId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Booking ID is required"
            );
        }

        if (!feedback.getBooking().getBookingId().equals(dto.getBookingId())) {

            if (feedbackRepository.existsByBooking_BookingId(dto.getBookingId())) {
                log.warn("Feedback update failed. Booking ID {} already has feedback",
                        dto.getBookingId());

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Feedback already exists for this booking"
                );
            }

            Booking booking = bookingRepository.findById(dto.getBookingId())
                    .orElseThrow(() -> {
                        log.warn("Booking not found during feedback update. ID: {}",
                                dto.getBookingId());

                        return new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Booking not found"
                        );
                    });

            feedback.setBooking(booking);
        }

        feedback.setRating(dto.getRating());
        feedback.setComments(dto.getComments());

        Feedback updatedFeedback = feedbackRepository.save(feedback);

        log.info("Feedback updated successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Feedback updated successfully",
                FeedbackMapper.convertToDTO(updatedFeedback)
        );
    }

    // DELETE FEEDBACK
    @Override
    public CommonResponseDto<Void> deleteFeedback(Integer id) {

        log.info("Deleting feedback. ID: {}", id);

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Feedback not found for deletion. ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Feedback not found"
                    );
                });

        feedbackRepository.delete(feedback);

        log.info("Feedback deleted successfully. ID: {}", id);

        return CommonResponseDto.success(
                "Feedback deleted successfully",
                null
        );
    }
}
