package com.desarrollox.learncompany.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BadgeRequest {
    private String name;
    private String urlIcon;
    private String criteria;
}