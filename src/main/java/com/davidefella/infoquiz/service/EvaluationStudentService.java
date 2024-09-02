package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.EvaluationStudent;
import com.davidefella.infoquiz.repository.EvaluationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationStudentService {

    private EvaluationStudentRepository evaluationStudentRepository;

    @Autowired
    public EvaluationStudentService(EvaluationStudentRepository evaluationStudentRepository) {
        this.evaluationStudentRepository = evaluationStudentRepository;
    }

    public List<EvaluationStudent> findAll() {
        return evaluationStudentRepository.findAll();
    }

    public Optional<EvaluationStudent> findById(Long id) {
        return evaluationStudentRepository.findById(id);
    }

    public EvaluationStudent save(EvaluationStudent evaluationStudent) {
        return evaluationStudentRepository.save(evaluationStudent);
    }

    public List<EvaluationStudent> findByEvaluationId(Long evaluationId){
        return evaluationStudentRepository.findByEvaluationId(evaluationId);
    }

    public void deleteById(Long id) {
        evaluationStudentRepository.deleteById(id);
    }
}
