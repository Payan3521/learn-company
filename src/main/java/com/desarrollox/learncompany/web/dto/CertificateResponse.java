package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;

public class CertificateResponse {
    private Long id;
    private UserResponse employee;
    private CourseResponse course;
    private LocalDateTime dateIssued;
}