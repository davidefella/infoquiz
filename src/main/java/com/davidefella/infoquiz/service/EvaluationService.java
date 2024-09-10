package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.davidefella.infoquiz.utility.scoresettings.ScoreConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EvaluationService {

    private EvaluationRepository evaluationRepository;

    private QuestionService questionService;

    private ScoreConfiguration scoreConfiguration;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository, QuestionService questionService, ScoreConfiguration scoreConfiguration) {
        this.evaluationRepository = evaluationRepository;
        this.questionService = questionService;
        this.scoreConfiguration = scoreConfiguration;
    }

    public Optional<Evaluation> findByUUID(UUID uuid) {
        return evaluationRepository.findByUuid(uuid);
    }

    public List<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }

    public Evaluation save(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public List<Evaluation> saveAll(List<Evaluation> evaluations) {
        return evaluationRepository.saveAll(evaluations);
    }

    public List<Evaluation> findByAssignedTeacher(Teacher teacher) {
        return evaluationRepository.findByAssignedTeacher(teacher);
    }

    //TODO: RIfattorizzare, passare l'oggetto
    public List<Evaluation> findByEmailAssignedTeacher(String email) {
        return evaluationRepository.findByAssignedTeacherEmail(email);
    }

}

