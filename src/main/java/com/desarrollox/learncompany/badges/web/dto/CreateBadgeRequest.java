package com.desarrollox.learncompany.badges.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBadgeRequest {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "URL icono must not exceed 500 characters")
    private String urlIcono;

    @NotBlank(message = "Criteria is required")
    @Size(min = 5, max = 500, message = "Criteria must be between 5 and 500 characters")
    private String criteria;
}