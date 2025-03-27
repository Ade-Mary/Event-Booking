package com.example.eventbooking.controller;

import com.example.eventbooking.JwtUtil;
import com.example.eventbooking.dto.BookingDto;
import com.example.eventbooking.model.User;
import com.example.eventbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{eventId}")
    public ResponseEntity<?> bookEvent(@PathVariable Long eventId,
                                       @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return ResponseEntity.ok(bookingService.bookEvent(eventId, user.getId()));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<BookingDto>> getUserBookings(
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return ResponseEntity.ok(bookingService.getUserBookings(user.getId()));
    }
}

