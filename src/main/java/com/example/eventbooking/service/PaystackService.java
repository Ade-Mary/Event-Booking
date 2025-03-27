package com.example.eventbooking.service;

import com.example.eventbooking.dto.PaystackPaymentRequest;
import com.example.eventbooking.dto.PaystackPaymentResponse;
import com.example.eventbooking.model.Event;
import com.example.eventbooking.model.User;
import com.example.eventbooking.repository.EventRepository;
import com.example.eventbooking.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;

@Service
@RequiredArgsConstructor
public class PaystackService {

    private final RestTemplate restTemplate;
    private final BookingService bookingService;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Value("${paystack.secretKey}")
    private String paystackSecretKey;

    @Value("${paystack.callbackUrl}")
    private String callbackUrl;

    public String initiatePayment(Long eventId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        int amount =  event.getAvailableTickets() * 10000; // Convert NGN to kobo

        PaystackPaymentRequest request = new PaystackPaymentRequest(
                user.getEmail(),
                amount,
                callbackUrl
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaystackPaymentRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<PaystackPaymentResponse> response = restTemplate
                .postForEntity("https://api.paystack.co/transaction/initialize", entity, PaystackPaymentResponse.class);

        if (response.getBody() != null && response.getBody().isStatus()) {
            return response.getBody().getData().getAuthorization_url();
        }
        throw new RuntimeException("Failed to initialize payment");
    }

    public boolean verifyPayment(String reference, Long eventId, Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<VerifyPaymentResponse> response = restTemplate.exchange(
                "https://api.paystack.co/transaction/verify/" + reference,
                HttpMethod.GET,
                entity,
                VerifyPaymentResponse.class
        );

        if (response.getBody() != null && "success".equals(response.getBody().getData().getStatus())) {
            bookingService.bookEvent(eventId, userId);
            return true;
        }
        return false;
    }
}

