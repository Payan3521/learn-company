package com.desarrollox.learncompany.web.dto;

import java.util.List;
import com.desarrollox.learncompany.domain.model.User.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String lastname;
    private boolean status;
    private Role role;
    private DepartmentResponse department;
    private String urlPhoto;
    private int puntos; // only for employee
    private List<BadgeResponse> badges; // only for employee
    private List<CertificateResponse> certificates; // only for employee
    private List<InscriptionResponse> inscriptions; // only for employee
    private String especialidad; // only for instructor
    private String biografia; // only for instructor
    private List<CourseResponse> courses; //only for instructor
    private Integer age; //only for administrator
}