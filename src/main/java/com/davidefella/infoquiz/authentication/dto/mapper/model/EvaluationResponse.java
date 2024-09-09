package com.davidefella.infoquiz.authentication.dto.mapper.model;

import com.davidefella.infoquiz.model.persistence.users.Teacher;
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
