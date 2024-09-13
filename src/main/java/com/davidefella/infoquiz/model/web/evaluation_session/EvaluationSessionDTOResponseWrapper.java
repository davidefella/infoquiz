package com.davidefella.infoquiz.model.web.evaluation_session;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EvaluationSessionDTOResponseWrapper {
    private List<EvaluationSessionDTOResponse> evaluationSessions;
}