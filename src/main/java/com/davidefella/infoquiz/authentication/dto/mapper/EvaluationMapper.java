package com.davidefella.infoquiz.authentication.dto.mapper;

import com.davidefella.infoquiz.authentication.dto.model.evaluation.EvaluationResponse;
import com.davidefella.infoquiz.model.persistence.Evaluation;

public class EvaluationMapper {

    public static EvaluationResponse toDto(Evaluation evaluation) {
        return new EvaluationResponse(
                evaluation.getAssignedTeacher().getUuid(),
                evaluation.getEvaluationDate(),
                evaluation.getTitle(),
                evaluation.getDescription(),
                evaluation.isActive()
        );
    }

}
