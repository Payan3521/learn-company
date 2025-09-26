package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgesController {
    
    @PostMapping
    public ResponseEntity<?> createBadge(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/by-name")
    public ResponseEntity<?> getBadgeByName(@RequestParam(required = true) String name){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getAllBadges(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBadgesById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getBadgesByEmployeeId(){
        throw new IllegalArgumentException();
    }
}