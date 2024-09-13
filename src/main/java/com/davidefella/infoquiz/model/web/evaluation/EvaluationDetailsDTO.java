package com.davidefella.infoquiz.model.web.evaluation;

import com.davidefella.infoquiz.model.web.question.QuestionDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EvaluationDetailsDTO {

    private UUID evaluationUuid;
    private String evaluationTitle;
    private List<QuestionDTOResponse> questions;
}
