
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.FeedbackDTO;
import com.cityride.cabservice.service.FeedbackService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class FeedbackController {

    private final FeedbackService feedbackService;

    // CREATE FEEDBACK
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DRIVER')")
    public ResponseEntity<CommonResponseDto<FeedbackDTO>> createFeedback(
            @RequestBody FeedbackDTO feedbackDTO) {

        log.info("REST request to create feedback");

        ResponseEntity<CommonResponseDto<FeedbackDTO>> response =
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(feedbackService.createFeedback(feedbackDTO));

        log.info("Feedback created successfully");

        return response;
    }

    // GET ALL FEEDBACK
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponseDto<List<FeedbackDTO>>> getAllFeedback() {

        log.info("REST request to retrieve all feedback");

        ResponseEntity<CommonResponseDto<List<FeedbackDTO>>> response =
                ResponseEntity.ok(feedbackService.getAllFeedback());

        log.info("Successfully retrieved all feedback");

        return response;
    }

    // GET FEEDBACK BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<CommonResponseDto<FeedbackDTO>> getFeedbackById(
            @PathVariable Integer id) {

        log.info("REST request to retrieve feedback. ID: {}", id);

        ResponseEntity<CommonResponseDto<FeedbackDTO>> response =
                ResponseEntity.ok(feedbackService.getFeedbackById(id));

        log.info("Feedback retrieved successfully. ID: {}", id);

        return response;
    }

    // UPDATE FEEDBACK
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<CommonResponseDto<FeedbackDTO>> updateFeedback(
            @PathVariable Integer id,
            @RequestBody FeedbackDTO feedbackDTO) {

        log.info("REST request to update feedback. ID: {}", id);

        ResponseEntity<CommonResponseDto<FeedbackDTO>> response =
                ResponseEntity.ok(
                        feedbackService.updateFeedback(id, feedbackDTO));

        log.info("Feedback updated successfully. ID: {}", id);

        return response;
    }

    // DELETE FEEDBACK
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<CommonResponseDto<Void>> deleteFeedback(
            @PathVariable Integer id) {

        log.info("REST request to delete feedback. ID: {}", id);

        ResponseEntity<CommonResponseDto<Void>> response =
                ResponseEntity.ok(feedbackService.deleteFeedback(id));

        log.info("Feedback deleted successfully. ID: {}", id);

        return response;
    }
}
