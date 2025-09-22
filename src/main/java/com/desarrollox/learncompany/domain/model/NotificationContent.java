package com.desarrollox.learncompany.domain.model;

public class NotificationContent {
    private Long id;
    private NotificationType type;
    private Long referenceId;
    private String message;
    private Notification notification;

    public NotificationContent() {
    }

    public NotificationContent(Long id, NotificationType type, Long referenceId, String message, Notification notification) {
        this.id = id;
        this.type = type;
        this.referenceId = referenceId;
        this.message = message;
        this.notification = notification;
    }

    public NotificationContent(NotificationType type, Long referenceId, String message, Notification notification) {
        this.type = type;
        this.referenceId = referenceId;
        this.message = message;
        this.notification = notification;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Notification getNotification() { return notification; }
    public void setNotification(Notification notification) { this.notification = notification; }

    public enum NotificationType {
        NEW_EVALUATION,
        RANKING_WINNER
    }
}