package com.desarrollox.learncompany.web.dto;

import com.desarrollox.learncompany.domain.model.NotificationContent.NotificationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationContentRequest {

    @NotNull(message = "El tipo de notificación es obligatorio")
    private NotificationType type;

    @NotNull(message = "El ID de la evaluacion es obligatorio")
    @Min(value = 1, message = "El ID de la evaluacion debe ser mayor que 0")
    private Long referenceId;
    
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    private String message;
}