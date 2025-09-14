package com.desarrollox.learncompany.api_users.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 👈 Lombok genera getters, setters, equals, hashCode y toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployRequest {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String rol;
    private String departament;
    private String urlFoto;
}
