package com.davidefella.infoquiz.authentication.dto.model.evaluation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EvaluationResponseWrapper {
    private List<EvaluationResponse> evaluations;
}
