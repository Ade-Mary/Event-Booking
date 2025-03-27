package com.example.eventbooking.model;

import com.example.eventbooking.repository.BookingRepository;
import com.example.eventbooking.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventReminderScheduler {

    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    @Scheduled(fixedRate = 3600000) // Runs every hour
    public void sendEventReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusHours(24); // 24 hours before event

        List<Booking> upcomingBookings = bookingRepository.findAll().stream()
                .filter(booking -> booking.getEvent().getDateTime() != null &&
                        booking.getEvent().getDateTime().isBefore(reminderTime) &&
                        booking.getEvent().getDateTime().isAfter(now))

                .toList();

        for (Booking booking : upcomingBookings) {
            emailService.sendEventReminder(booking.getAttendee().getEmail(),
                    booking.getEvent().getName(),
                    booking.getEvent().getDateTime() );
        }

        log.info("Sent reminders for {} upcoming events", upcomingBookings.size());
    }
}
