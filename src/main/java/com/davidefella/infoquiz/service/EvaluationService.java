package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.web.evaluation.EvaluationDetailsDTO;
import com.davidefella.infoquiz.model.web.evaluation.mapper.EvaluationMapper;
import com.davidefella.infoquiz.model.web.question.QuestionDTOResponse;
import com.davidefella.infoquiz.repository.EvaluationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationMapper evaluationMapper;

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public List<Evaluation> saveAll(List<Evaluation> evaluations) {
        return evaluationRepository.saveAll(evaluations);
    }

    public Evaluation findByUUID(UUID uuid) {
        return evaluationRepository.findByUuid(uuid)  .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with UUID: " + uuid));
    }

    public List<Evaluation> findByEmailAssignedTeacher(String email) {
        return evaluationRepository.findByAssignedTeacherEmail(email);
    }

    public EvaluationDetailsDTO getEvaluationWithQuestions(UUID evaluationUuid) {
        // Recupera l'entità Evaluation in base all'UUID
        Evaluation evaluation = evaluationRepository.findByUuid(evaluationUuid)
                .orElseThrow(() -> new EntityNotFoundException("Evaluation not found"));

        // Usa il mapper per convertire l'entità Evaluation in EvaluationDetailsDTO,
        // che include anche le domande e risposte
        return evaluationMapper.toEvaluationDetailsDTO(evaluation);
    }
}

