package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Notification;

public interface INotificationService {
    Notification createNotification(Notification notification);
    Optional<Notification> getNotificationById(Long id);
    List<Notification> getAllNotifications();
    List<Notification> getNotificationsByUserId(Long userId);
    Notification markAsRead(Long notificationId);
}
