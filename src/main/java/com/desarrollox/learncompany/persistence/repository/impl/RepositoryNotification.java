package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryNotification;
import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.persistence.mapper.NotificationMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryNotification;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryNotification implements IRepositoryNotification {

    private final JpaRepositoryNotification jpaRepositoryNotification;
    private final NotificationMapper notificationMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Notification save(Notification notification) {
        loggingService.logInfo("Iniciando guardado de Notification para userId: {}", 
                notification.getUser().getId() != null ? notification.getUser().getId() : "null");
        try {
            Notification savedNotification = notificationMapper.toDomain(
                    jpaRepositoryNotification.save(
                            notificationMapper.toEntity(notification)
                    )
            );
            loggingService.logInfo("Notification guardada exitosamente con ID: {} para userId: {}", 
                    savedNotification.getId(), 
                    savedNotification.getUser().getId());
            return savedNotification;
        } catch (Exception e) {
            loggingService.logError("Error al guardar Notification para userId {}: {}", 
                    notification.getUser().getId() != null ? notification.getUser().getId() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Notification> findById(Long id) {
        loggingService.logInfo("Obteniendo Notification con ID: {}", id);
        try {
            Optional<Notification> notification = jpaRepositoryNotification.findById(id)
                    .map(notificationMapper::toDomain);
            if (notification.isPresent()) {
                loggingService.logInfo("Notification ID {} obtenida exitosamente", id);
            } else {
                loggingService.logWarning("Notification con ID {} no encontrada", id);
            }
            return notification;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Notification ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Notification> findAll() {
        loggingService.logInfo("Obteniendo todas las Notifications");
        try {
            List<Notification> notifications = jpaRepositoryNotification.findAll()
                    .stream()
                    .map(notificationMapper::toDomain)
                    .collect(Collectors.toList());
            if (notifications.isEmpty()) {
                loggingService.logWarning("No se encontraron Notifications");
            } else {
                loggingService.logInfo("Se encontraron {} Notifications", notifications.size());
            }
            return notifications;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todas las Notifications: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Notification> findNotificationsByUserId(Long userId) {
        loggingService.logInfo("Obteniendo Notifications para userId: {}", userId);
        try {
            List<Notification> notifications = jpaRepositoryNotification.findByUserId(userId)
                    .stream()
                    .map(notificationMapper::toDomain)
                    .collect(Collectors.toList());
            if (notifications.isEmpty()) {
                loggingService.logWarning("No se encontraron Notifications para userId: {}", userId);
            } else {
                loggingService.logInfo("Se encontraron {} Notifications para userId: {}", notifications.size(), userId);
            }
            return notifications;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Notifications para userId {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        loggingService.logInfo("Iniciando marcado como leída de Notification con ID: {}", notificationId);
        try {
            Notification updatedNotification = jpaRepositoryNotification.findById(notificationId)
                    .map(entity -> {
                        entity.setReadStatus(true);
                        Notification savedNotification = notificationMapper.toDomain(
                                jpaRepositoryNotification.save(entity)
                        );
                        loggingService.logInfo("Notification ID {} marcada como leída exitosamente", notificationId);
                        return savedNotification;
                    })
                    .orElse(null);
            if (updatedNotification == null) {
                loggingService.logWarning("Notification con ID {} no encontrada para marcar como leída", notificationId);
            }
            return updatedNotification;
        } catch (Exception e) {
            loggingService.logError("Error al marcar como leída Notification ID {}: {}", notificationId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de Notification con ID: {}", id);
        try {
            boolean exists = jpaRepositoryNotification.existsById(id);
            loggingService.logDebug("Notification con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Notification ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}