package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Notification;

public interface IRepositoryNotification {
    Notification save(Notification notification);
    Optional<Notification> findById(Long id);
    List<Notification> findAll();
    List<Notification> findNotificationsByUserId(Long userId);
    Notification markAsRead(Long notificationId);
    boolean existsById(Long id);
}