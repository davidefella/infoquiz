package com.davidefella.infoquiz.controller.api;

import com.davidefella.infoquiz.model.web.evaluation.*;
import com.davidefella.infoquiz.model.web.evaluation.mapper.EvaluationMapper;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.service.EvaluationService;
import com.davidefella.infoquiz.service.UserInfoQuizService;
import com.davidefella.infoquiz.utility.LogConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService, UserInfoQuizService userInfoQuizService) {
        this.evaluationService = evaluationService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.TEACHER_EVALUATIONS_V1)
    public EvaluationDTOResponseWrapper getEvaluationsForTeacher(Authentication authentication) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
        String teacherEmail = jwtAuthToken.getName();

        log.info("{} Richiesta di ottenimento valutazioni per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, teacherEmail);

        List<Evaluation> evaluations;
        try {
            evaluations = evaluationService.findByEmailAssignedTeacher(teacherEmail);
            log.debug("{} Trovate {} valutazioni per l'insegnante {}", LogConstants.LOG_TAG_INFOQUIZ, evaluations.size(), teacherEmail);
        } catch (Exception e) {
            log.error("{} Errore nel recuperare le valutazioni per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, teacherEmail, e);
            throw e;
        }

        List<EvaluationDTOResponse> dtoResponses = new ArrayList<>();
        for (Evaluation evaluation : evaluations) {
            dtoResponses.add(EvaluationMapper.toEvaluationDto(evaluation));
        }

        log.info("{} Restituzione di {} valutazioni per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, dtoResponses.size(), teacherEmail);
        return new EvaluationDTOResponseWrapper(dtoResponses);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.TEACHER_EVALUATIONS_V1 + "/{uuid}")
    public EvaluationDetailsDTO getEvaluationDetails(@PathVariable UUID uuid) {
        log.info("{} Richiesta di dettagli per la valutazione con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);

        EvaluationDetailsDTO evaluationDetails;
        try {
            evaluationDetails = evaluationService.getEvaluationWithQuestions(uuid);
            log.debug("{} Dettagli della valutazione trovati per UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        } catch (EntityNotFoundException e) {
            log.warn("{} Valutazione non trovata per UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
            throw e;
        } catch (Exception e) {
            log.error("{} Errore nel recuperare i dettagli della valutazione con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid, e);
            throw e;
        }

        log.info("{} Restituzione dettagli per la valutazione con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        return evaluationDetails;
    }
}
