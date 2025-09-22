package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private User user;
    private String title;
    private NotificationContent content;
    private LocalDateTime dateIssued;
    private boolean read;

    public Notification() {
    }

    public Notification(User user, String title, NotificationContent content, LocalDateTime dateIssued, boolean read) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.dateIssued = dateIssued;
        this.read = read;
    }

    public Notification(Long id, User user, String title, NotificationContent content, LocalDateTime dateIssued, boolean read) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.dateIssued = dateIssued;
        this.read = read;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public NotificationContent getContent() { return content; }
    public void setContent(NotificationContent content) { this.content = content; }

    public LocalDateTime getDateIssued() { return dateIssued; }
    public void setDateIssued(LocalDateTime dateIssued) { this.dateIssued = dateIssued; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

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
}