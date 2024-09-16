package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.web.evaluation.EvaluationDetailsDTO;
import com.davidefella.infoquiz.model.web.evaluation.mapper.EvaluationMapper;
import com.davidefella.infoquiz.repository.EvaluationRepository;
import com.davidefella.infoquiz.utility.LogConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EvaluationService {

    @Autowired
    private EvaluationMapper evaluationMapper;

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public List<Evaluation> saveAll(List<Evaluation> evaluations) {
        log.info("{} Inizio salvataggio di {} evaluations", LogConstants.LOG_TAG_INFOQUIZ, evaluations.size());
        List<Evaluation> savedEvaluations = evaluationRepository.saveAll(evaluations);
        log.info("{} Salvataggio completato di {} evaluations", LogConstants.LOG_TAG_INFOQUIZ, savedEvaluations.size());
        return savedEvaluations;
    }

    public Evaluation findByUUID(UUID uuid) {
        log.info("{} Ricerca della valutazione con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        Evaluation evaluation = evaluationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with UUID: " + uuid));
        log.info("{} Valutazione trovata con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        return evaluation;
    }

    public List<Evaluation> findByEmailAssignedTeacher(String email) {
        log.info("{} Ricerca delle valutazioni assegnate all'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, email);
        List<Evaluation> evaluations = evaluationRepository.findByAssignedTeacherEmail(email);
        log.info("{} Trovate {} valutazioni per l'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, evaluations.size(), email);
        return evaluations;
    }

    public EvaluationDetailsDTO getEvaluationWithQuestions(UUID evaluationUuid) {
        log.info("{} Ricerca dettagli della valutazione con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, evaluationUuid);
        Evaluation evaluation = evaluationRepository.findByUuid(evaluationUuid)
                .orElseThrow(() -> new EntityNotFoundException("Evaluation not found"));

        EvaluationDetailsDTO dto = evaluationMapper.toEvaluationDetailsDTO(evaluation);
        log.info("{} Dettagli della valutazione trovati per UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, evaluationUuid);
        return dto;
    }
}
