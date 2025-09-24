package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AnswerResponse {
    private Long id;
    private String content;
    private Long questionId;
    private LocalDateTime dateIssued;
    private Long assessmentInstanceId;
}