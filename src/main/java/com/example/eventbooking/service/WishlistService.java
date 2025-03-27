package com.example.eventbooking.service;

import com.example.eventbooking.model.Event;
import com.example.eventbooking.model.User;
import com.example.eventbooking.model.Wishlist;
import com.example.eventbooking.repository.EventRepository;
import com.example.eventbooking.repository.UserRepository;
import com.example.eventbooking.repository.WishlistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public Wishlist addToWishlist(Long userId, Long eventId) {
        if (wishlistRepository.existsByUserIdAndEventId(userId, eventId)) {
            throw new IllegalStateException("Event already in wishlist");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setEvent(event);

        return wishlistRepository.save(wishlist);
    }

    public List<Event> getUserWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId)
                .stream()
                .map(Wishlist::getEvent)
                .collect(Collectors.toList());
    }

    public void removeFromWishlist(Long userId, Long eventId) {
        if (!wishlistRepository.existsByUserIdAndEventId(userId, eventId)) {
            throw new IllegalStateException("Event not in wishlist");
        }
        wishlistRepository.deleteByUserIdAndEventId(userId, eventId);
    }
}
