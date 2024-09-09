package com.davidefella.infoquiz.controller.api;

import com.davidefella.infoquiz.authentication.dto.mapper.EvaluationMapper;
import com.davidefella.infoquiz.authentication.dto.model.evaluation.EvaluationResponse;
import com.davidefella.infoquiz.authentication.dto.model.evaluation.EvaluationResponseWrapper;
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

    private UserInfoQuizService userInfoQuizService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService, UserInfoQuizService userInfoQuizService) {
        this.evaluationService = evaluationService;
        this.userInfoQuizService = userInfoQuizService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.EVALUATIONS_V1)
    public EvaluationResponseWrapper getEvaluationsForTeacher(Authentication authentication) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;

        List<Evaluation> evaluations = evaluationService.findByEmailAssignedTeacher(jwtAuthToken.getName());

        List<EvaluationResponse> dtoEvaluations = new ArrayList<>();
        for (Evaluation evaluation : evaluations) {
            EvaluationResponse dto = EvaluationMapper.toDto(evaluation);
            dtoEvaluations.add(dto);
        }

        return new EvaluationResponseWrapper(dtoEvaluations);  // Avvolgi le evaluation in un wrapper
    }
}