package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryNotification;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.NotificationNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.domain.service.INotificationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final IRepositoryNotification repositoryNotification;
    private final IRepositoryUser repositoryUser;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Notification createNotification(Notification notification) {
        loggingService.logInfo("Iniciando creación de Notification para userId: {}", notification.getUser().getId());
        try {
            if (!repositoryUser.existsById(notification.getUser().getId())) {
                loggingService.logError("Usuario con ID {} no encontrado", notification.getUser().getId());
                throw new UserNotFoundException(notification.getUser().getId());
            }

            loggingService.logDebug("Obteniendo User con ID: {}", notification.getUser().getId());
            notification.setUser(repositoryUser.findById(notification.getUser().getId()).get());

            Notification savedNotification = repositoryNotification.save(notification);
            loggingService.logInfo("Notification creada exitosamente con ID: {} para userId: {}", 
                    savedNotification.getId(), savedNotification.getUser().getId());
            return savedNotification;
        } catch (Exception e) {
            loggingService.logError("Error al crear Notification para userId {}: {}", 
                    notification.getUser().getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Notification> getNotificationById(Long id) {
        loggingService.logInfo("Obteniendo Notification con ID: {}", id);
        try {
            if (!repositoryNotification.existsById(id)) {
                loggingService.logError("Notification con ID {} no encontrada", id);
                throw new NotificationNotFoundException(id);
            }
            Optional<Notification> notification = repositoryNotification.findById(id);
            loggingService.logInfo("Notification ID {} obtenida exitosamente", id);
            return notification;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Notification ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notification> getAllNotifications() {
        loggingService.logInfo("Obteniendo todas las Notifications");
        try {
            List<Notification> notifications = repositoryNotification.findAll();
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

    @Transactional(readOnly = true)
    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        loggingService.logInfo("Obteniendo Notifications para userId: {}", userId);
        try {
            if (!repositoryUser.existsById(userId)) {
                loggingService.logError("Usuario con ID {} no encontrado", userId);
                throw new UserNotFoundException(userId);
            }
            List<Notification> notifications = repositoryNotification.findNotificationsByUserId(userId);
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

    @Transactional(readOnly = false)
    @Override
    public Notification markAsRead(Long notificationId) {
        loggingService.logInfo("Iniciando marcación como leída de Notification con ID: {}", notificationId);
        try {
            if (!repositoryNotification.existsById(notificationId)) {
                loggingService.logError("Notification con ID {} no encontrada", notificationId);
                throw new NotificationNotFoundException(notificationId);
            }
            Notification updatedNotification = repositoryNotification.markAsRead(notificationId);
            loggingService.logInfo("Notification ID {} marcada como leída exitosamente", notificationId);
            return updatedNotification;
        } catch (Exception e) {
            loggingService.logError("Error al marcar como leída Notification ID {}: {}", notificationId, e.getMessage(), e);
            throw e;
        }
    }
}