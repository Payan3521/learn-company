package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.NotificationEntity;

@Repository
public interface JpaRepositoryNotification extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUserId(Long userId);
    
}