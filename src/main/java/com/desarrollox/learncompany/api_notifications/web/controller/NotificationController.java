package com.desarrollox.learncompany.api_notifications.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import com.desarrollox.learncompany.api_notifications.web.dto.NotificationRequest;
import com.desarrollox.learncompany.api_notifications.web.dto.NotificationResponse;
import com.desarrollox.learncompany.api_users.web.dto.UserResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@RequestBody NotificationRequest notificationRequest){
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Notificacion registrada correctamente", null));
    }

    @GetMapping("/getAllNotifications")
    public ResponseEntity<ApiResponse<NotificationResponse>> getAllNotification(){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Notificaciones encontradas", null));
    }

     @GetMapping("/getNotificationById/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getByIdNotification(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Notificacion encontrada", null));
    }
    
    @GetMapping("/userGetById/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getByIdUser(@Parameter Long id){
       
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Usuario encontrado", null));
    } 

    @PatchMapping("/mark-read/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> markNotification(@Valid @PathVariable String id, @RequestBody NotificationRequest notificationRequest){
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Notificacion marcada correctamente", null));
    }

}
