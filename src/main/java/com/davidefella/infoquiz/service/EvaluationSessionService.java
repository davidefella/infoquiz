package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import com.davidefella.infoquiz.model.persistence.EvaluationSession;
import com.davidefella.infoquiz.repository.EvaluationSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EvaluationSessionService {

    private final EvaluationSessionRepository evaluationSessionRepository;

    @Autowired
    public EvaluationSessionService(EvaluationSessionRepository evaluationSessionRepository) {
        this.evaluationSessionRepository = evaluationSessionRepository;
    }

    public List<EvaluationSession> saveAll(List<EvaluationSession> evaluationsSessions) {
        return evaluationSessionRepository.saveAll(evaluationsSessions);
    }

    public EvaluationSession findByUUID(UUID uuid) {
        return evaluationSessionRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("EvaluationSession not found with UUID: " + uuid));
    }

    public List<EvaluationSession> findByEvaluationUuid(UUID uuid) {
        return evaluationSessionRepository.findByEvaluationUuid(uuid);
    }
}

