package com.example.eventbooking.service;

import com.example.eventbooking.model.Booking;
import com.example.eventbooking.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final BookingRepository bookingRepository;

    public String checkInUser(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        if (booking.isCheckedIn()) {
            return "User has already checked in.";
        }

        booking.setCheckedIn(true);
        bookingRepository.save(booking);
        return "Check-in successful!";
    }
}
