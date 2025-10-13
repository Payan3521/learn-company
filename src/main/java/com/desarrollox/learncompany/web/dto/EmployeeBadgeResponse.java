package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class EmployeeBadgeResponse {
    private Long id;
    private Long employeeId;
    private Long badgeId;
    private LocalDateTime dateEarned;
}