package com.cityride.cabservice.repository;


import com.cityride.cabservice.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    boolean existsByBooking_BookingId(Integer bookingId);
}