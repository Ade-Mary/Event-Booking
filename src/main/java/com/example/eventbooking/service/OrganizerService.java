package com.example.eventbooking.service;

import com.example.eventbooking.dto.EventSummaryDto;
import com.example.eventbooking.model.Event;
import com.example.eventbooking.model.Review; // ✅ Import Review model
import com.example.eventbooking.repository.BookingRepository;
import com.example.eventbooking.repository.EventRepository;
import com.example.eventbooking.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizerService {
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    public List<EventSummaryDto> getOrganizerEvents(Long organizerId) {
        List<Event> events = eventRepository.findByOrganizerId(organizerId);

        return events.stream().map(event -> {
            int totalRegistrations = bookingRepository.countByEventId(event.getId());
            double averageRating = reviewRepository.findByEventId(event.getId())
                    .stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            return new EventSummaryDto(
                    event.getId(),
                    event.getName(),
                    event.getDateTime(), // ✅ Fixed method name
                    totalRegistrations,
                    averageRating
            );
        }).collect(Collectors.toList());
    }
}
