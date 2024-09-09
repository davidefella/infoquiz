package com.davidefella.infoquiz.authentication.dto.model.evaluation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EvaluationResponse {

    private UUID uuidAssignedTeacher;
    private LocalDate evaluationDate;
    private String title;
    private String description;
    private boolean isActive;
}
