package com.example.eventbooking.controller;

import com.example.eventbooking.model.Event;
import com.example.eventbooking.model.Wishlist;
import com.example.eventbooking.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<Wishlist> addToWishlist(@PathVariable Long userId,
                                                  @PathVariable Long eventId) {
        return ResponseEntity.ok(wishlistService.addToWishlist(userId, eventId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Event>> getUserWishlist(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userId));
    }

    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long userId,
                                                     @PathVariable Long eventId) {
        wishlistService.removeFromWishlist(userId, eventId);
        return ResponseEntity.ok("Event removed from wishlist");
    }
}

