package com.desarrollox.learncompany.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class FeedBackResponse {
    private Long id;
    private String question;
    private String answer;
}