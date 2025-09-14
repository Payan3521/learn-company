package com.desarrollox.learncompany.badges.web.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.badges.web.dto.BadgeResponse;
import com.desarrollox.learncompany.badges.web.dto.CreateBadgeRequest;
import com.desarrollox.learncompany.badges.web.webMapper.BadgeWebMapper;
import com.desarrollox.learncompany.common.web.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/learncompany/api/badges")
public class BadgeController {
    
    private final BadgeWebMapper badgeWebMapper;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BadgeResponse>> createBadge(@Valid @RequestBody CreateBadgeRequest request) {
        BadgeResponse response = badgeWebMapper.toBadgeResponse(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Badge created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<BadgeResponse>> getBadgeByName(@RequestParam String name) {
        BadgeResponse response = badgeWebMapper.toBadgeResponse(1L, name);
        return ResponseEntity.ok(ApiResponse.success("Badge found successfully", response));
    }

    @GetMapping("/getAllBadges")
    public ResponseEntity<ApiResponse<List<BadgeResponse>>> getAllBadges() {
        List<BadgeResponse> badges = Arrays.asList(
            badgeWebMapper.toBadgeResponse(1L, "Badge 1"),
            badgeWebMapper.toBadgeResponse(2L, "Badge 2")
        );
        return ResponseEntity.ok(ApiResponse.success("Badges retrieved successfully", badges));
    }

    @GetMapping("/getBadgeId")
    public ResponseEntity<ApiResponse<BadgeResponse>> getBadgeById() {
        BadgeResponse response = badgeWebMapper.toBadgeResponse(1L, "Sample Badge");
        return ResponseEntity.ok(ApiResponse.success("Badge found successfully", response));
    }
}