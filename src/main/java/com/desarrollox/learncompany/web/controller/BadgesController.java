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
import com.desarrollox.learncompany.core.logging.LoggingService;
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
    private final LoggingService loggingService; // Inyectar LoggingService

    @PostMapping
    public ResponseEntity<ApiResponse<BadgeResponse>> createBadge(@Valid @RequestBody BadgeRequest request) {
        loggingService.logInfo("Iniciando creación de Badge con nombre: {}", 
                truncateName(request != null && request.getName() != null ? request.getName() : "null"));
        try {
            Badge badge = badgeWebMapper.requestToDomain(request);
            Badge badgeSaved = badgeService.createBadge(badge);
            BadgeResponse response = badgeWebMapper.domainToResponse(badgeSaved);
            loggingService.logInfo("Badge creado exitosamente con ID: {} y nombre: {}", 
                    badgeSaved.getId(), truncateName(badgeSaved.getName()));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Badge creado correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear Badge con nombre {}: {}", 
                    truncateName(request != null && request.getName() != null ? request.getName() : "null"), 
                    e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BadgeResponse>>> getAllBadges() {
        loggingService.logInfo("Obteniendo todos los Badges");
        try {
            List<Badge> badges = badgeService.getAllBadges();

            if (badges.isEmpty()) {
                loggingService.logWarning("No se encontraron Badges");
                return ResponseEntity.noContent().build();
            }

            List<BadgeResponse> badgeResponses = badges.stream()
                    .map(badgeWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Badges", badgeResponses.size());
            return ResponseEntity.ok(ApiResponse.success("Badges encontrados", badgeResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Badges: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BadgeResponse>> getBadgesById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Badge con ID: {}", id);
        try {
            Badge badge = badgeService.getBadgeById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Badge con ID " + id + " no encontrado"));
            BadgeResponse badgeResponse = badgeWebMapper.domainToResponse(badge);
            loggingService.logInfo("Badge ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Badge encontrado", badgeResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<ApiResponse<List<BadgeResponse>>> getBadgesByEmployeeId(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Badges para employeeId: {}", id);
        try {
            List<Badge> badges = badgeService.getBadgesByEmployeeId(id);

            if (badges.isEmpty()) {
                loggingService.logWarning("No se encontraron Badges para employeeId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<BadgeResponse> badgeResponses = badges.stream()
                    .map(badgeWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Badges para employeeId: {}", badgeResponses.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Badge correspondientes al empleado: " + id, badgeResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badges para employeeId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse<BadgeResponse>> getBadgeByName(@RequestParam(required = true) String name) {
        loggingService.logInfo("Obteniendo Badge con nombre: {}", truncateName(name));
        try {
            Badge badge = badgeService.findByName(name)
                    .orElseThrow(() -> new IllegalArgumentException("Badge con nombre " + name + " no encontrado"));
            BadgeResponse badgeResponse = badgeWebMapper.domainToResponse(badge);
            loggingService.logInfo("Badge con nombre {} obtenido exitosamente", truncateName(name));
            return ResponseEntity.ok(ApiResponse.success("Badge encontrado", badgeResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badge con nombre {}: {}", truncateName(name), e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar nombres de insignias en los logs
    private String truncateName(String name) {
        if (name == null) {
            return "null";
        }
        return name.length() > 30 ? name.substring(0, 30) + "..." : name;
    }
}