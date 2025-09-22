package com.desarrollox.learncompany.web.dto;

import com.desarrollox.learncompany.domain.model.NotificationContent.NotificationType;

public class NotificationContentRequest {
    private NotificationType type;
    private Long referenceId;
    private String message;
    //ya conoce la notificacion
    private Long notificationId;
}