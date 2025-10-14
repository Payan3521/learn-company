package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = false)
    @Override
    public Notification createNotification(Notification notification) {
        if(!repositoryUser.existsById(notification.getUser().getId())){
            throw new UserNotFoundException(notification.getUser().getId());
        }

        notification.setUser(repositoryUser.findById(notification.getUser().getId()).get());
        
        return repositoryNotification.save(notification);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Notification> getNotificationById(Long id) {
        if(!repositoryNotification.existsById(id)){
            throw new NotificationNotFoundException(id);
        }
        return repositoryNotification.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notification> getAllNotifications() {
        return repositoryNotification.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        if(!repositoryUser.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        return repositoryNotification.findNotificationsByUserId(userId);
    }

    @Transactional(readOnly = false)
    @Override
    public Notification markAsRead(Long notificationId) {
        if(!repositoryNotification.existsById(notificationId)){
            throw new NotificationNotFoundException(notificationId);
        }
        return repositoryNotification.markAsRead(notificationId);
    }

}