package com.cityride.cabservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Setter
@Getter
@ToString(exclude = "booking")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Integer feedbackId;

    @OneToOne
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            unique = true,
            referencedColumnName = "booking_id"
    )
    private Booking booking;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "feedback_date", nullable = false, updatable = false)
    private LocalDateTime feedbackDate;

    @PrePersist
    protected void onCreate() {
        feedbackDate = LocalDateTime.now();
    }
}