package com.desarrollox.learncompany.web.dto;

import com.desarrollox.learncompany.domain.model.NotificationContent.NotificationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class NotificationContentResponse {
    private Long id;
    private NotificationType type;
    private Long referenceId;
    private String message;
    //aqui se estaría redundando
    private NotificationResponse notification;
}