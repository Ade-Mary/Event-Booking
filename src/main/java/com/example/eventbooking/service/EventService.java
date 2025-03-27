package com.example.eventbooking.service;

import com.example.eventbooking.dto.EventDto;
import com.example.eventbooking.model.Event;
import com.example.eventbooking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventDto createEvent(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setDateTime(eventDto.getDateTime());
        event.setLocation(eventDto.getLocation());
        event.setAvailableTickets(eventDto.getAvailableTickets());

        Event savedEvent = eventRepository.save(event);
        return new EventDto(
                savedEvent.getId(),
                savedEvent.getName(),
                savedEvent.getDescription(),
                savedEvent.getDateTime(),
                savedEvent.getLocation(),
                savedEvent.getAvailableTickets()
        );
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(event -> new EventDto(
                        event.getId(),
                        event.getName(),
                        event.getDescription(),
                        event.getDateTime(),
                        event.getLocation(),
                        event.getAvailableTickets()
                ))
                .collect(Collectors.toList());
    }

    public EventDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return new EventDto(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getDateTime(),
                event.getLocation(),
                event.getAvailableTickets()
        );
    }
}

