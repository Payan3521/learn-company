package com.desarrollox.learncompany.badges.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class BadgeResponse {
    private Long id;
    private String name;
    private String urlIcono;
    private String criteria;
    private boolean active;
}