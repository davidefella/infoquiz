package com.davidefella.infoquiz.model.web.evaluation.mapper;

import com.davidefella.infoquiz.model.web.evaluation.EvaluationResponse;
import com.davidefella.infoquiz.model.persistence.Evaluation;

public class EvaluationMapper {

    private EvaluationMapper(){}

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
