package com.example.eventbooking.controller;

import com.example.eventbooking.dto.UserProfileUpdateDto;
import com.example.eventbooking.model.User;
import com.example.eventbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long userId,
                                                  @RequestBody UserProfileUpdateDto updateDto) {
        return ResponseEntity.ok(userService.updateProfile(userId, updateDto));
    }
}

