package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryNotification;
import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.domain.service.INotificationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final IRepositoryNotification repositoryNotification;

    @Override
    public Notification createNotification(Notification notification) {
        return repositoryNotification.save(notification);
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return repositoryNotification.findById(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return repositoryNotification.findAll();
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return repositoryNotification.findNotificationsByUserId(userId);
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        return repositoryNotification.markAsRead(notificationId);
    }
    

}