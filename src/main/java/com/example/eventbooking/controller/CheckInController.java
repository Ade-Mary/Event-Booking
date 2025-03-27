package com.example.eventbooking.controller;

import com.example.eventbooking.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check-in")
@RequiredArgsConstructor
public class CheckInController {
    private final CheckInService checkInService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<String> checkInUser(@PathVariable Long bookingId) {
        return ResponseEntity.ok(checkInService.checkInUser(bookingId));
    }
}

