package com.desarrollox.learncompany.persistence.entity;

import com.desarrollox.learncompany.domain.model.NotificationContent.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notifications_content")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class NotificationContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private NotificationType type;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "message")
    private String message;

    private NotificationEntity notification;

}
