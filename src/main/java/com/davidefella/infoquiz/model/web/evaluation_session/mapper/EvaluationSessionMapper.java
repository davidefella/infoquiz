package com.davidefella.infoquiz.model.web.evaluation_session.mapper;

import com.davidefella.infoquiz.model.persistence.EvaluationSession;
import com.davidefella.infoquiz.model.web.evaluation_session.EvaluationSessionDTOResponse;

public class EvaluationSessionMapper {

    private EvaluationSessionMapper() {}

    public static EvaluationSessionDTOResponse toEvaluationSessionDto(EvaluationSession session) {
        return new EvaluationSessionDTOResponse(
                session.getUuid(),
                session.getStartTime(),
                session.getEndTime(),
                session.getEvaluation().isActive(),
                session.getEvaluation().getUuid(),
                session.getStatus()
        );
    }
}