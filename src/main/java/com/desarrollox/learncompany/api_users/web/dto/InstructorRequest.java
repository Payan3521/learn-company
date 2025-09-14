package com.desarrollox.learncompany.api_users.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorRequest {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String rol;
    private String departament;
    private String urlFoto;
}
