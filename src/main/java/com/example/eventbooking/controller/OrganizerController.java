package com.example.eventbooking.controller;

import com.example.eventbooking.dto.EventSummaryDto;
import com.example.eventbooking.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizer")
@RequiredArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;

    @GetMapping("/{organizerId}/events")
    public ResponseEntity<List<EventSummaryDto>> getOrganizerEvents(@PathVariable Long organizerId) {
        return ResponseEntity.ok(organizerService.getOrganizerEvents(organizerId));
    }
}

