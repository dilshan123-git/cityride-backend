package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.FeedbackDTO;
import com.cityride.cabservice.service.FeedbackService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DRIVER')")
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(feedbackService.createFeedback(feedbackDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedback() {

        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<FeedbackDTO> getFeedbackById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<FeedbackDTO> updateFeedback(
            @PathVariable Integer id,
            @RequestBody FeedbackDTO feedbackDTO) {

        return ResponseEntity.ok(
                feedbackService.updateFeedback(id, feedbackDTO)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<String> deleteFeedback(
            @PathVariable Integer id) {

        feedbackService.deleteFeedback(id);

        return ResponseEntity.ok("Feedback deleted successfully");
    }
}
