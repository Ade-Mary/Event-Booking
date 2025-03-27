package com.example.eventbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaystackPaymentRequest {
    private String email;
    private int amount;  // Amount in kobo (NGN 100 = 10000)
    private String callbackUrl;
}
