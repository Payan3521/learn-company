package com.desarrollox.learncompany.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class BadgeResponse {
    private Long id;
    private String name;
    private String urlIcon;
    private String criteria;
    private UserResponse employee;
}