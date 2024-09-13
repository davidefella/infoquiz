package com.davidefella.infoquiz.model.web.evaluation_session;

import com.davidefella.infoquiz.model.persistence.EvaluationSessionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EvaluationSessionDTOResponse {
    private UUID uuid;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean evaluationIsActive;
    private UUID evaluationUuid;
    private EvaluationSessionStatus evaluationSessionStatus;
}