package com.example.eventbooking.controller;

import com.example.eventbooking.dto.ReviewDto;
import com.example.eventbooking.model.Review;
import com.example.eventbooking.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<Review> submitReview(@PathVariable Long userId,
                                               @PathVariable Long eventId,
                                               @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.submitReview(userId, eventId, reviewDto));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Review>> getEventReviews(@PathVariable Long eventId) {
        return ResponseEntity.ok(reviewService.getEventReviews(eventId));
    }
}

