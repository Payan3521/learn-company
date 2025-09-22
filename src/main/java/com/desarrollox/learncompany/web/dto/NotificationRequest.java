package com.desarrollox.learncompany.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationRequest {
    private Long userId;
    private String title;
    private NotificationContentRequest content;
}