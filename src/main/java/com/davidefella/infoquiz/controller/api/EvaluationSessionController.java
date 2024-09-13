package com.davidefella.infoquiz.controller.api;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.EvaluationSession;
import com.davidefella.infoquiz.model.web.evaluation.EvaluationDTOResponse;
import com.davidefella.infoquiz.model.web.evaluation.EvaluationDTOResponseWrapper;
import com.davidefella.infoquiz.model.web.evaluation.mapper.EvaluationMapper;
import com.davidefella.infoquiz.model.web.evaluation_session.EvaluationSessionDTOResponse;
import com.davidefella.infoquiz.model.web.evaluation_session.EvaluationSessionDTOResponseWrapper;
import com.davidefella.infoquiz.model.web.evaluation_session.mapper.EvaluationSessionMapper;
import com.davidefella.infoquiz.service.EvaluationSessionService;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class EvaluationSessionController {

    private final EvaluationSessionService evaluationSessionService;

    @Autowired
    public EvaluationSessionController(EvaluationSessionService evaluationSessionService) {
        this.evaluationSessionService = evaluationSessionService;
    }
    // uuid della evaluation

    // uuid della evaluation
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.TEACHER_SESSIONS_V1 + "/{uuid}")
    public EvaluationSessionDTOResponseWrapper getEvaluationSessionsForTeacher(@PathVariable UUID uuid, Authentication authentication) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;

        List<EvaluationSession> evaluationSessions = evaluationSessionService.findByEvaluationUuid(uuid);

        // Conversione delle entità in DTO
        List<EvaluationSessionDTOResponse> dtoResponses = new ArrayList<>();

        for (EvaluationSession es : evaluationSessions) {
            dtoResponses.add(EvaluationSessionMapper.toEvaluationSessionDto(es));
        }

        return new EvaluationSessionDTOResponseWrapper(dtoResponses);
    }
}