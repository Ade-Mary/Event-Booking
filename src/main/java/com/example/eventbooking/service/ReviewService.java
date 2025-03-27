package com.example.eventbooking.service;

import com.example.eventbooking.dto.ReviewDto;
import com.example.eventbooking.model.Event;
import com.example.eventbooking.model.Review;
import com.example.eventbooking.model.User;
import com.example.eventbooking.repository.BookingRepository;
import com.example.eventbooking.repository.EventRepository;
import com.example.eventbooking.repository.ReviewRepository;
import com.example.eventbooking.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Review submitReview(Long userId, Long eventId, ReviewDto reviewDto) {
        // Ensure user has booked the event before reviewing
        boolean hasBooked = bookingRepository.existsByAttendeeIdAndEventId(userId, eventId);
        if (!hasBooked) {
            throw new IllegalStateException("User must attend the event before reviewing");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        Review review = new Review();
        review.setReviewer(user);
        review.setEvent(event);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        return reviewRepository.save(review);
    }

    public List<Review> getEventReviews(Long eventId) {
        return reviewRepository.findByEventId(eventId);
    }
}

