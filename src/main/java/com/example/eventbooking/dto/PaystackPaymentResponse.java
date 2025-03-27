package com.example.eventbooking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaystackPaymentResponse {
    private boolean status;
    private String message;
    private PaystackData data;

    @Getter
    @Setter
    public static class PaystackData {
        private String authorization_url;
        private String reference;
    }
}

