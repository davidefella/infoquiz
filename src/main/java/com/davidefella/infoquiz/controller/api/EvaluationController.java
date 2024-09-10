package com.davidefella.infoquiz.controller.api;

import com.davidefella.infoquiz.model.web.evaluation.mapper.EvaluationMapper;
import com.davidefella.infoquiz.model.web.evaluation.EvaluationDTOResponse;
import com.davidefella.infoquiz.model.web.evaluation.EvaluationDTOResponseWrapper;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.service.EvaluationService;
import com.davidefella.infoquiz.service.UserInfoQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class EvaluationController {

    private EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService, UserInfoQuizService userInfoQuizService) {
        this.evaluationService = evaluationService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.TEACHER_EVALUATIONS_V1)
    public EvaluationDTOResponseWrapper getEvaluationsForTeacher(Authentication authentication) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;

        List<Evaluation> evaluations = evaluationService.findByEmailAssignedTeacher(jwtAuthToken.getName());

        List<EvaluationDTOResponse> dtoResponses = new ArrayList<>();
        EvaluationDTOResponse dto = null;

        for (Evaluation evaluation : evaluations) {
            dtoResponses.add(EvaluationMapper.toEvaluationDto(evaluation));
        }

        return new EvaluationDTOResponseWrapper(dtoResponses);
    }
}