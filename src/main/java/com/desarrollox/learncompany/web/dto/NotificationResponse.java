package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;

public class NotificationResponse {
    private Long id;
    private UserResponse user;
    private String title;
    private NotificationContentResponse content;
    private LocalDateTime dateIssued;
    private boolean read;
}