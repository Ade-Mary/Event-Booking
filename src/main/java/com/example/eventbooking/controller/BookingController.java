package com.example.eventbooking.controller;

import com.example.eventbooking.JwtUtil;
import com.example.eventbooking.dto.BookingDto;
import com.example.eventbooking.model.Booking;
import com.example.eventbooking.model.User;
import com.example.eventbooking.repository.UserRepository;
import com.example.eventbooking.service.BookingService;
import com.example.eventbooking.service.PdfGeneratorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;
    private final PdfGeneratorService pdfGeneratorService;

    private final JwtUtil jwtUtil;

    @PostMapping("/{eventId}")
    public ResponseEntity<?> bookEvent(@PathVariable Long eventId,
                                       @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return ResponseEntity.ok(bookingService.bookEvent(eventId, user.getId()));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<BookingDto>> getUserBookings(
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return ResponseEntity.ok(bookingService.getUserBookings(user.getId()));
    }

    @GetMapping("/{bookingId}/ticket")
    public ResponseEntity<byte[]> downloadTicket(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        byte[] pdfBytes = pdfGeneratorService.generateBookingTicket(booking);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket.pdf")
                .body(pdfBytes);
    }

    @GetMapping("/{bookingId}/qr")
    public ResponseEntity<String> getQRCode(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking.getQrCode());
    }
}

