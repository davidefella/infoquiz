package com.davidefella.infoquiz.model.web.evaluation.mapper;

import com.davidefella.infoquiz.model.web.evaluation.EvaluationDTOResponse;
import com.davidefella.infoquiz.model.persistence.Evaluation;

public class EvaluationMapper {

    private EvaluationMapper(){}

    public static EvaluationDTOResponse toEvaluationDto(Evaluation evaluation) {
        return new EvaluationDTOResponse(
                evaluation.getUuid(),
                evaluation.getAssignedTeacher().getUuid(),
                evaluation.getEvaluationDate(),
                evaluation.getTitle(),
                evaluation.getDescription(),
                evaluation.isActive()
        );
    }

}
