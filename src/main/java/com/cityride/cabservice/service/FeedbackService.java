package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {

    FeedbackDTO createFeedback(FeedbackDTO feedbackDTO);

    List<FeedbackDTO> getAllFeedback();

    FeedbackDTO getFeedbackById(Integer id);

    FeedbackDTO updateFeedback(Integer id, FeedbackDTO feedbackDTO);

    void deleteFeedback(Integer id);
}