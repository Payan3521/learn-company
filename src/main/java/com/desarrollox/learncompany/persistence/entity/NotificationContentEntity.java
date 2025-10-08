package com.desarrollox.learncompany.persistence.entity;

import com.desarrollox.learncompany.domain.model.NotificationContent.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "type_notification", nullable = false)
    private NotificationType type;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId;

    @Column(name = "message_notification", nullable = false)
    private String message;

    @OneToOne(mappedBy = "content")
    private NotificationEntity notification;

}