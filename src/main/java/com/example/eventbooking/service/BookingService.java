package com.example.eventbooking.service;

import com.example.eventbooking.dto.BookingDto;
import com.example.eventbooking.model.Booking;
import com.example.eventbooking.model.Event;
import com.example.eventbooking.model.User;
import com.example.eventbooking.repository.BookingRepository;
import com.example.eventbooking.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingDto bookEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (event.getAvailableTickets() <= 0) {
            throw new IllegalStateException("No tickets available for this event");
        }

        if (bookingRepository.existsByEventIdAndAttendeeId(eventId, userId)) {
            throw new IllegalStateException("User has already booked this event");
        }

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setAttendee(user);
        booking.setBookingTime(LocalDateTime.now());

        event.setAvailableTickets(event.getAvailableTickets() - 1);
        eventRepository.save(event);
        bookingRepository.save(booking);

        return new BookingDto(eventId, userId, booking.getBookingTime());
    }

    public List<BookingDto> getUserBookings(Long userId) {
        return bookingRepository.findByAttendeeId(userId)
                .stream()
                .map(b -> new BookingDto(b.getEvent().getId(), userId, b.getBookingTime()))
                .collect(Collectors.toList());
    }
}

