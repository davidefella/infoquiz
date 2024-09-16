package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import com.davidefella.infoquiz.model.persistence.EvaluationSession;
import com.davidefella.infoquiz.repository.EvaluationSessionRepository;
import com.davidefella.infoquiz.utility.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EvaluationSessionService {

    private final EvaluationSessionRepository evaluationSessionRepository;

    @Autowired
    public EvaluationSessionService(EvaluationSessionRepository evaluationSessionRepository) {
        this.evaluationSessionRepository = evaluationSessionRepository;
    }

    public List<EvaluationSession> saveAll(List<EvaluationSession> evaluationsSessions) {
        log.info("{} Inizio salvataggio di {} sessioni di valutazione", LogConstants.LOG_TAG_INFOQUIZ, evaluationsSessions.size());
        List<EvaluationSession> savedSessions = evaluationSessionRepository.saveAll(evaluationsSessions);
        log.info("{} Salvataggio completato di {} sessioni di valutazione", LogConstants.LOG_TAG_INFOQUIZ, savedSessions.size());
        return savedSessions;
    }

    public EvaluationSession findByUUID(UUID uuid) {
        log.info("{} Ricerca della sessione di valutazione con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        EvaluationSession evaluationSession = evaluationSessionRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("EvaluationSession not found with UUID: " + uuid));
        log.info("{} Sessione di valutazione trovata con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        return evaluationSession;
    }

    public List<EvaluationSession> findByEvaluationUuid(UUID uuid) {
        log.info("{} Ricerca delle sessioni di valutazione per l'UUID della valutazione: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        List<EvaluationSession> sessions = evaluationSessionRepository.findByEvaluationUuid(uuid);
        log.info("{} Trovate {} sessioni di valutazione per l'UUID della valutazione: {}", LogConstants.LOG_TAG_INFOQUIZ, sessions.size(), uuid);
        return sessions;
    }
}
