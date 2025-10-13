package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.domain.service.IBadgeService;
import com.desarrollox.learncompany.web.dto.BadgeRequest;
import com.desarrollox.learncompany.web.dto.BadgeResponse;
import com.desarrollox.learncompany.web.webMapper.BadgeWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgesController {

    private final IBadgeService badgeService;
    private final BadgeWebMapper badgeWebMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<BadgeResponse>> createBadge(@Valid @RequestBody BadgeRequest request){
        Badge badge = badgeWebMapper.requestToDomain(request);
        Badge badgeSaved = badgeService.createBadge(badge);
        BadgeResponse response = badgeWebMapper.domainToResponse(badgeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Badge creado correctamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BadgeResponse>>> getAllBadges(){
        List<Badge> badges = badgeService.getAllBadges();

        if(badges.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<BadgeResponse> badgeResponses = badges.stream().map(badgeWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Badges encontrados", badgeResponses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BadgeResponse>> getBadgesById(@PathVariable Long id){
        Badge badge = badgeService.getBadgeById(id).get();
        BadgeResponse badgeResponse= badgeWebMapper.domainToResponse(badge);
        return ResponseEntity.ok(ApiResponse.success("Badge encontrado ", badgeResponse));
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<ApiResponse<List<BadgeResponse>>> getBadgesByEmployeeId(@PathVariable Long id){
        List<Badge> badges = badgeService.getBadgesByEmployeeId(id);

        if(badges.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<BadgeResponse> badgeResponses = badges.stream().map(badgeWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Badge correspondientes al empleado: " +id , badgeResponses));
    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse<BadgeResponse>> getBadgeByName(@RequestParam(required = true) String name){
        Badge badge = badgeService.findByName(name).get();
        BadgeResponse badgeResponse = badgeWebMapper.domainToResponse(badge);
        return ResponseEntity.ok(ApiResponse.success("Badge encontrado", badgeResponse));
    }
}