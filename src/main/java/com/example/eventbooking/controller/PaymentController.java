package com.example.eventbooking.controller;

import com.example.eventbooking.JwtUtil;
import com.example.eventbooking.model.User;
import com.example.eventbooking.repository.UserRepository;
import com.example.eventbooking.service.PaystackService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaystackService paystackService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/initiate/{eventId}")
    public ResponseEntity<String> initiatePayment(@PathVariable Long eventId,
                                                  @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String paymentUrl = paystackService.initiatePayment(eventId, user.getId());
        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestParam("reference") String reference,
                                                @RequestParam("eventId") Long eventId,
                                                @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        boolean paymentVerified = paystackService.verifyPayment(reference, eventId, user.getId());
        return paymentVerified ? ResponseEntity.ok("Payment Successful") : ResponseEntity.badRequest().body("Payment Failed");
    }
}

