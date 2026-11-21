
package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {

    CommonResponseDto<FeedbackDTO> createFeedback(FeedbackDTO dto);

    CommonResponseDto<List<FeedbackDTO>> getAllFeedback();

    CommonResponseDto<FeedbackDTO> getFeedbackById(Integer id);

    CommonResponseDto<FeedbackDTO> updateFeedback(Integer id, FeedbackDTO dto);

    CommonResponseDto<Void> deleteFeedback(Integer id);
}
