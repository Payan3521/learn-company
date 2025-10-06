package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssignBadgeRequest {
    
    @NotNull(message = "El ID del empleado es obligatorio")
    private Long employeeId;
    
    @NotNull(message = "El ID del badge es obligatorio")
    private Long badgeId;
}
