package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private User user;
    private String title;
    private NotificationContent content;
    private LocalDateTime dateIssued;
    private boolean readStatus;

    public Notification() {
    }

    public Notification(User user, String title, NotificationContent content, LocalDateTime dateIssued, boolean readStatus) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.dateIssued = dateIssued;
        this.readStatus = readStatus;
    }

    public Notification(Long id, User user, String title, NotificationContent content, LocalDateTime dateIssued, boolean readStatus) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.dateIssued = dateIssued;
        this.readStatus = readStatus;
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

    public boolean isReadStatus() { return readStatus; }
    public void setReadStatus(boolean readStatus) { this.readStatus = readStatus; }

}