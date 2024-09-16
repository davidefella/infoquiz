package com.davidefella.infoquiz.controller.api;

import com.davidefella.infoquiz.model.persistence.EvaluationSession;
import com.davidefella.infoquiz.model.web.evaluation_session.EvaluationSessionDTOResponse;
import com.davidefella.infoquiz.model.web.evaluation_session.EvaluationSessionDTOResponseWrapper;
import com.davidefella.infoquiz.model.web.evaluation_session.mapper.EvaluationSessionMapper;
import com.davidefella.infoquiz.service.EvaluationSessionService;
import com.davidefella.infoquiz.utility.LogConstants;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class EvaluationSessionController {

    private final EvaluationSessionService evaluationSessionService;

    @Autowired
    public EvaluationSessionController(EvaluationSessionService evaluationSessionService) {
        this.evaluationSessionService = evaluationSessionService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(ApiEndpoints.TEACHER_SESSIONS_V1 + "/{uuid}")
    public EvaluationSessionDTOResponseWrapper getEvaluationSessionsForTeacher(@PathVariable UUID uuid, Authentication authentication) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
        String teacherEmail = jwtAuthToken.getName();

        log.info("{} Richiesta di ottenimento sessioni di valutazione per l'insegnante con email: {} e valutazione UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, teacherEmail, uuid);

        List<EvaluationSession> evaluationSessions;
        try {
            evaluationSessions = evaluationSessionService.findByEvaluationUuid(uuid);
            log.debug("{} Trovate {} sessioni di valutazione per UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, evaluationSessions.size(), uuid);
        } catch (Exception e) {
            log.error("{} Errore nel recuperare le sessioni di valutazione per UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid, e);
            throw e;
        }

        List<EvaluationSessionDTOResponse> dtoResponses = new ArrayList<>();
        for (EvaluationSession es : evaluationSessions) {
            dtoResponses.add(EvaluationSessionMapper.toEvaluationSessionDto(es));
        }

        log.info("{} Restituzione di {} sessioni di valutazione per UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, dtoResponses.size(), uuid);
        return new EvaluationSessionDTOResponseWrapper(dtoResponses);
    }
}
