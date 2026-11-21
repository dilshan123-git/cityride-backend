package com.cityride.cabservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {

    private Integer feedbackId;
    private Integer bookingId;
    private Integer rating;
    private String comments;
}