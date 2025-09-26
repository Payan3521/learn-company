package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationsController {
    
    @PostMapping
    public ResponseEntity<?> createNotification(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getAllNotifications(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationsById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUsersById(){
        throw new IllegalArgumentException();
    }

    @PatchMapping("/mark-read")
    public ResponseEntity<?> markNotificationRead(){
        throw new IllegalArgumentException();
    }
}