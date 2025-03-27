package com.example.eventbooking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEventReminder(String recipientEmail, String eventName, LocalDateTime eventDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Reminder: Upcoming Event - " + eventName);
        message.setText("Hello,\n\n"
                + "This is a reminder that your event '" + eventName + "' is happening on " + eventDate + ".\n"
                + "We look forward to seeing you there!\n\n"
                + "Best regards,\nEvent Booking Team");

        mailSender.send(message);
    }
}
