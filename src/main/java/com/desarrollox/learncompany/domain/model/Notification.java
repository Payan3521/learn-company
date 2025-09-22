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

}