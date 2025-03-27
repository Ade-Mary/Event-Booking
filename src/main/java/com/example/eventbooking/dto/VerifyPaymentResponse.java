package com.example.eventbooking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyPaymentResponse {
    private boolean status;
    private String message;
    private PaystackVerificationData data;

    @Getter
    @Setter
    public static class PaystackVerificationData {
        private String status;
        private String reference;
        private String gateway_response;
    }
}

