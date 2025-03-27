package com.example.eventbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor  // Ensures no-argument constructor is available
@AllArgsConstructor // Ensures a constructor that accepts all fields
public class EventDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String location;
    private int availableTickets;
}

