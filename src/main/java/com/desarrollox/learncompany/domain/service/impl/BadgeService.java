package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.BadgeAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.BadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.domain.service.IBadgeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BadgeService implements IBadgeService {

    private final IRepositoryBadge repositoryBadge;
    private final IRepositoryUser repositoryUser;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Badge createBadge(Badge badge) {
        loggingService.logInfo("Iniciando creación de Badge con nombre: {}", badge.getName());
        try {
            if (repositoryBadge.findBadgeByName(badge.getName()).isPresent()) {
                loggingService.logError("Badge con nombre {} ya está registrado", badge.getName());
                throw new BadgeAlreadyRegisteredException(badge.getName());
            }
            Badge savedBadge = repositoryBadge.save(badge);
            loggingService.logInfo("Badge creado exitosamente con ID: {} y nombre: {}", savedBadge.getId(), savedBadge.getName());
            return savedBadge;
        } catch (Exception e) {
            loggingService.logError("Error al crear Badge con nombre {}: {}", badge.getName(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Badge> getBadgeById(Long id) {
        loggingService.logInfo("Obteniendo Badge con ID: {}", id);
        try {
            if (!repositoryBadge.existsById(id)) {
                loggingService.logError("Badge con ID {} no encontrado", id);
                throw new BadgeNotFoundException(id);
            }
            Optional<Badge> badge = repositoryBadge.findById(id);
            loggingService.logInfo("Badge ID {} obtenido exitosamente", id);
            return badge;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Badge> getAllBadges() {
        loggingService.logInfo("Obteniendo todas las Badges");
        try {
            List<Badge> badges = repositoryBadge.findAll();
            if (badges.isEmpty()) {
                loggingService.logWarning("No se encontraron Badges");
            } else {
                loggingService.logInfo("Se encontraron {} Badges", badges.size());
            }
            return badges;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todas las Badges: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Badge> getBadgesByEmployeeId(Long employeeId) {
        loggingService.logInfo("Obteniendo Badges para employeeId: {}", employeeId);
        try {
            if (!repositoryUser.existsById(employeeId)) {
                loggingService.logError("Usuario con ID {} no encontrado", employeeId);
                throw new UserNotFoundException(employeeId);
            }
            List<Badge> badges = repositoryBadge.findBadgesByEmployeeId(employeeId);
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Badge> findByName(String name) {
        loggingService.logInfo("Obteniendo Badge con nombre: {}", name);
        try {
            Optional<Badge> badge = repositoryBadge.findBadgeByName(name);
            if (!badge.isPresent()) {
                loggingService.logError("Badge con nombre {} no encontrado", name);
                throw new BadgeNotFoundException("No existe badge con nombre: " + name);
            }
            loggingService.logInfo("Badge con nombre {} obtenido exitosamente", name);
            return badge;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badge con nombre {}: {}", name, e.getMessage(), e);
            throw e;
        }
    }
}