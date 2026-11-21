package com.cityride.cabservice.mapper;

import com.cityride.cabservice.dto.FeedbackDTO;
import com.cityride.cabservice.entity.Feedback;

public class FeedbackMapper {

    public static FeedbackDTO convertToDTO(Feedback feedback) {

        FeedbackDTO dto = new FeedbackDTO();

        dto.setFeedbackId(feedback.getFeedbackId());
        dto.setBookingId(feedback.getBooking().getBookingId());
        dto.setRating(feedback.getRating());
        dto.setComments(feedback.getComments());

        return dto;
    }

}
