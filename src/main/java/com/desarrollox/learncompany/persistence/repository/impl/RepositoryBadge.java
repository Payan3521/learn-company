package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.persistence.mapper.BadgeMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryBadge;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryBadge implements IRepositoryBadge {

    private final JpaRepositoryBadge jpaRepositoryBadge;
    private final BadgeMapper badgeMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Badge save(Badge badge) {
        loggingService.logInfo("Iniciando guardado de Badge con nombre: {}", badge.getName());
        try {
            Badge savedBadge = badgeMapper.toDomain(
                    jpaRepositoryBadge.save(
                            badgeMapper.toEntity(badge)
                    )
            );
            loggingService.logInfo("Badge guardado exitosamente con ID: {} y nombre: {}", 
                    savedBadge.getId(), savedBadge.getName());
            return savedBadge;
        } catch (Exception e) {
            loggingService.logError("Error al guardar Badge con nombre {}: {}", 
                    badge.getName(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Badge> findById(Long id) {
        loggingService.logInfo("Obteniendo Badge con ID: {}", id);
        try {
            Optional<Badge> badge = jpaRepositoryBadge.findById(id)
                    .map(badgeMapper::toDomain);
            if (badge.isPresent()) {
                loggingService.logInfo("Badge ID {} obtenido exitosamente", id);
            } else {
                loggingService.logWarning("Badge con ID {} no encontrado", id);
            }
            return badge;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Badge> findAll() {
        loggingService.logInfo("Obteniendo todos los Badges");
        try {
            List<Badge> badges = jpaRepositoryBadge.findAll()
                    .stream()
                    .map(badgeMapper::toDomain)
                    .collect(Collectors.toList());
            if (badges.isEmpty()) {
                loggingService.logWarning("No se encontraron Badges");
            } else {
                loggingService.logInfo("Se encontraron {} Badges", badges.size());
            }
            return badges;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Badges: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Badge> findBadgesByEmployeeId(Long employeeId) {
        loggingService.logInfo("Obteniendo Badges para employeeId: {}", employeeId);
        try {
            List<Badge> badges = jpaRepositoryBadge.findBadgesByEmployeeId(employeeId)
                    .stream()
                    .map(badgeMapper::toDomain)
                    .collect(Collectors.toList());
            if (badges.isEmpty()) {
                loggingService.logWarning("No se encontraron Badges para employeeId: {}", employeeId);
            } else {
                loggingService.logInfo("Se encontraron {} Badges para employeeId: {}", badges.size(), employeeId);
            }
            return badges;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badges para employeeId {}: {}", employeeId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Badge> findBadgeByName(String name) {
        loggingService.logInfo("Obteniendo Badge con nombre: {}", name);
        try {
            Optional<Badge> badge = jpaRepositoryBadge.findBadgeByName(name)
                    .map(badgeMapper::toDomain);
            if (badge.isPresent()) {
                loggingService.logInfo("Badge con nombre {} obtenido exitosamente", name);
            } else {
                loggingService.logWarning("Badge con nombre {} no encontrado", name);
            }
            return badge;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badge con nombre {}: {}", name, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de Badge con ID: {}", id);
        try {
            boolean exists = jpaRepositoryBadge.existsById(id);
            loggingService.logDebug("Badge con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Badge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}