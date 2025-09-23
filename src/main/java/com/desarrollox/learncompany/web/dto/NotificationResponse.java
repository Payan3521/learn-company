package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class NotificationResponse {
    private Long id;
    private UserResponse user;
    private String title;
    private NotificationContentResponse content;
    private LocalDateTime dateIssued;
    private boolean readStatus;
}