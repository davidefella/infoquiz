package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.EvaluationPlayer;
import com.davidefella.infoquiz.repository.EvaluationPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationPlayerService {

    private EvaluationPlayerRepository evaluationPlayerRepository;

    @Autowired
    public EvaluationPlayerService(EvaluationPlayerRepository evaluationPlayerRepository) {
        this.evaluationPlayerRepository = evaluationPlayerRepository;
    }

    public List<EvaluationPlayer> findAll() {
        return evaluationPlayerRepository.findAll();
    }

    public Optional<EvaluationPlayer> findById(Long id) {
        return evaluationPlayerRepository.findById(id);
    }

    public EvaluationPlayer save(EvaluationPlayer evaluationPlayer) {
        return evaluationPlayerRepository.save(evaluationPlayer);
    }

    public void deleteById(Long id) {
        evaluationPlayerRepository.deleteById(id);
    }
}
