package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.domain.service.INotificationService;
import com.desarrollox.learncompany.web.dto.NotificationRequest;
import com.desarrollox.learncompany.web.dto.NotificationResponse;
import com.desarrollox.learncompany.web.webMapper.NotificationWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final INotificationService notificationService;
    private final NotificationWebMapper notificationWebMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@Valid @RequestBody NotificationRequest request){
        Notification notification = notificationWebMapper.requestToDomain(request);
        Notification createdNotification = notificationService.createNotification(notification);
        NotificationResponse response = notificationWebMapper.domainToResponse(createdNotification);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Notificacion creada correctamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications(){
        List<Notification> notifications = notificationService.getAllNotifications();

        if(notifications.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<NotificationResponse> response = notifications.stream().map(notificationWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Notificaciones obtenidas correctamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotificationsById(@PathVariable Long id){
        Notification notification = notificationService.getNotificationById(id).get();
        NotificationResponse response = notificationWebMapper.domainToResponse(notification);
        return ResponseEntity.ok(ApiResponse.success("Notificacion obtenida correctamente", response));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUsersById(@PathVariable Long id){
        List<Notification> notifications = notificationService.getNotificationsByUserId(id);

        if(notifications.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        
        List<NotificationResponse> response = notifications.stream().map(notificationWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Notificaciones obtenidas correctamente", response));
    }

    @PatchMapping("/mark-read/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> markNotificationRead(@PathVariable Long id){
        Notification notification = notificationService.markAsRead(id);
        NotificationResponse response = notificationWebMapper.domainToResponse(notification);
        return ResponseEntity.ok(ApiResponse.success("Notificacion marcada como leida correctamente", response));
    }

}