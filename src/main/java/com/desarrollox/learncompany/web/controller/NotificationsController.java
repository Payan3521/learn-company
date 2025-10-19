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
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.domain.service.INotificationService;
import com.desarrollox.learncompany.web.dto.NotificationRequest;
import com.desarrollox.learncompany.web.dto.NotificationResponse;
import com.desarrollox.learncompany.web.webMapper.NotificationWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(
    name = "Notifications",
    description = "Endpoints para la gestión de notificaciones de usuarios dentro de la organización."
)
public class NotificationsController {

    private final INotificationService notificationService;
    private final NotificationWebMapper notificationWebMapper;
    private final LoggingService loggingService;

    @Operation(
        summary = "Crear una nueva notificación",
        description = "Permite crear una nueva notificación para un usuario específico.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "userId": 1,
                            "title": "Nueva evaluación disponible",
                            "content": {
                                "type": "ASSESSMENT",
                                "referenceId": 5,
                                "message": "Tienes una nueva evaluación pendiente"
                            }
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Notificación creada correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Notificacion creada correctamente",
                                "data": {
                                    "id": 1,
                                    "userId": 1,
                                    "title": "Nueva evaluación disponible",
                                    "content": {
                                        "id": 1,
                                        "type": "ASSESSMENT",
                                        "referenceId": 5,
                                        "message": "Tienes una nueva evaluación pendiente",
                                        "notificationId": 1
                                    },
                                    "dateIssued": "2025-10-18T15:35:00.123456789",
                                    "readStatus": false
                                },
                                "timestamp": "2025-10-18T15:35:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@Valid @RequestBody NotificationRequest request) {
        loggingService.logInfo("Iniciando creación de Notification para userId: {} y title: {}", 
                request != null && request.getUserId() != null ? request.getUserId() : "null", 
                request != null && request.getTitle() != null ? request.getTitle() : "null");
        try {
            Notification notification = notificationWebMapper.requestToDomain(request);
            Notification createdNotification = notificationService.createNotification(notification);
            NotificationResponse response = notificationWebMapper.domainToResponse(createdNotification);
            loggingService.logInfo("Notification creada exitosamente con ID: {} para userId: {}", 
                    createdNotification.getId(), createdNotification.getUser().getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Notificacion creada correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear Notification para userId: {} y title: {}: {}", 
                    request != null && request.getUserId() != null ? request.getUserId() : "null", 
                    request != null && request.getTitle() != null ? request.getTitle() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener todas las notificaciones",
        description = "Devuelve la lista completa de notificaciones registradas en el sistema.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Notificaciones obtenidas correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Notificaciones obtenidas correctamente",
                                "data": [
                                    {
                                        "id": 1,
                                        "userId": 1,
                                        "title": "Nueva evaluación disponible",
                                        "content": {
                                            "id": 1,
                                            "type": "ASSESSMENT",
                                            "referenceId": 5,
                                            "message": "Tienes una nueva evaluación pendiente",
                                            "notificationId": 1
                                        },
                                        "dateIssued": "2025-10-18T15:35:00",
                                        "readStatus": false
                                    },
                                    {
                                        "id": 2,
                                        "userId": 2,
                                        "title": "Curso completado",
                                        "content": {
                                            "id": 2,
                                            "type": "COURSE",
                                            "referenceId": 3,
                                            "message": "Has completado el curso exitosamente",
                                            "notificationId": 2
                                        },
                                        "dateIssued": "2025-10-18T15:35:00",
                                        "readStatus": true
                                    }
                                ],
                                "timestamp": "2025-10-18T15:35:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications() {
        loggingService.logInfo("Obteniendo todas las Notifications");
        try {
            List<Notification> notifications = notificationService.getAllNotifications();

            if (notifications.isEmpty()) {
                loggingService.logWarning("No se encontraron Notifications");
                return ResponseEntity.noContent().build();
            }

            List<NotificationResponse> response = notifications.stream()
                    .map(notificationWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Notifications", response.size());
            return ResponseEntity.ok(ApiResponse.success("Notificaciones obtenidas correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todas las Notifications: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener notificación por ID",
        description = "Devuelve la información detallada de una notificación según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la notificación",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Notificación obtenida correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Notificacion obtenida correctamente",
                                "data": {
                                    "id": 1,
                                    "userId": 1,
                                    "title": "Nueva evaluación disponible",
                                    "content": {
                                        "id": 1,
                                        "type": "ASSESSMENT",
                                        "referenceId": 5,
                                        "message": "Tienes una nueva evaluación pendiente",
                                        "notificationId": 1
                                    },
                                    "dateIssued": "2025-10-18T15:35:00",
                                    "readStatus": false
                                },
                                "timestamp": "2025-10-18T15:35:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Notificación no encontrada", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotificationsById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Notification con ID: {}", id);
        try {
            Notification notification = notificationService.getNotificationById(id).get();
            NotificationResponse response = notificationWebMapper.domainToResponse(notification);
            loggingService.logInfo("Notification ID {} obtenida exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Notificacion obtenida correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Notification ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener notificaciones por usuario",
        description = "Devuelve la lista de notificaciones asociadas a un usuario específico.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del usuario",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Notificaciones obtenidas correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Notificaciones obtenidas correctamente",
                                "data": [
                                    {
                                        "id": 1,
                                        "userId": 1,
                                        "title": "Nueva evaluación disponible",
                                        "content": {
                                            "id": 1,
                                            "type": "ASSESSMENT",
                                            "referenceId": 5,
                                            "message": "Tienes una nueva evaluación pendiente",
                                            "notificationId": 1
                                        },
                                        "dateIssued": "2025-10-18T15:35:00",
                                        "readStatus": false
                                    }
                                ],
                                "timestamp": "2025-10-18T15:35:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado para filtrar", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUsersById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Notifications para userId: {}", id);
        try {
            List<Notification> notifications = notificationService.getNotificationsByUserId(id);

            if (notifications.isEmpty()) {
                loggingService.logWarning("No se encontraron Notifications para userId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<NotificationResponse> response = notifications.stream()
                    .map(notificationWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Notifications para userId: {}", response.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Notificaciones obtenidas correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Notifications para userId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Marcar notificación como leída",
        description = "Actualiza el estado de una notificación para marcarla como leída.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la notificación",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Notificación marcada como leída correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Notificacion marcada como leida correctamente",
                                "data": {
                                    "id": 1,
                                    "userId": 1,
                                    "title": "Nueva evaluación disponible",
                                    "content": {
                                        "id": 1,
                                        "type": "ASSESSMENT",
                                        "referenceId": 5,
                                        "message": "Tienes una nueva evaluación pendiente",
                                        "notificationId": 1
                                    },
                                    "dateIssued": "2025-10-18T15:35:00",
                                    "readStatus": true
                                },
                                "timestamp": "2025-10-18T15:35:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Notificación no encontrada", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PatchMapping("/mark-read/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> markNotificationRead(@PathVariable Long id) {
        loggingService.logInfo("Iniciando marcado como leída de Notification con ID: {}", id);
        try {
            Notification notification = notificationService.markAsRead(id);
            NotificationResponse response = notificationWebMapper.domainToResponse(notification);
            loggingService.logInfo("Notification ID {} marcada como leída exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Notificacion marcada como leida correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al marcar como leída Notification ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}