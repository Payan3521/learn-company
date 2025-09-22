package com.desarrollox.learncompany.persistence.entity;

import java.time.LocalDateTime;

import com.desarrollox.learncompany.domain.model.Notification.NotificationContent;
import com.desarrollox.learncompany.domain.model.Notification.NotificationContent.NotificationType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private NotificationContentEntity content;

    @Column(name = "date_issued")
    private LocalDateTime dateIssued;

    @Column(name = "read")
    private boolean read;
} 