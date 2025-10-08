package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
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
    
    @Override
    public Notification save(Notification notification) {
        return notificationMapper.toDomain(
                jpaRepositoryNotification.save(
                        notificationMapper.toEntity(notification)
                )
        );
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return jpaRepositoryNotification.findById(id)
                .map(notificationMapper::toDomain);
    }

    @Override
    public List<Notification> findAll() {
        return jpaRepositoryNotification.findAll()
                .stream()
                .map(notificationMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Notification> findNotificationsByUserId(Long userId) {
        return jpaRepositoryNotification.findByUserId(userId)
                .stream()
                .map(notificationMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        return jpaRepositoryNotification.findById(notificationId)
                .map(entity -> {
                    entity.setReadStatus(true);
                    return notificationMapper.toDomain(
                            jpaRepositoryNotification.save(entity)
                    );
                }).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryNotification.existsById(id);
    }
    
}