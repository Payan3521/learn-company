package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class NotificationsController {
    
    public ResponseEntity<?> createNotification(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getAllNotifications(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getNotificationsById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getUsersById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> markNotificationRead(){
        throw new IllegalArgumentException();
    }
}