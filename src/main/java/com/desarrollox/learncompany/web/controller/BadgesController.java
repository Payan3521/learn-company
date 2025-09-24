package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class BadgesController {
    
    public ResponseEntity<?> createBadge(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getBadgeByName(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getAllBadges(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getBadgesById(){
        throw new IllegalArgumentException();
    }
}
