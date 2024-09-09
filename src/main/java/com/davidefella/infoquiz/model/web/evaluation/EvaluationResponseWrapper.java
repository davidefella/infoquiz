package com.davidefella.infoquiz.model.web.evaluation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EvaluationResponseWrapper {
    private List<EvaluationResponse> evaluations;
}
