package com.example.eventbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventSummaryDto {
    private Long eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private int totalRegistrations;
    private double averageRating;
}

