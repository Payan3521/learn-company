package com.desarrollox.learncompany.api_badges.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeRequest {
    private String nombre;
    private String urlIcono;
    private String criterio; 
}
