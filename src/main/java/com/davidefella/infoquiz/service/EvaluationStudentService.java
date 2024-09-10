package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.EvaluationStudent;
import com.davidefella.infoquiz.repository.EvaluationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationStudentService {

    private EvaluationStudentRepository evaluationStudentRepository;

    @Autowired
    public EvaluationStudentService(EvaluationStudentRepository evaluationStudentRepository) {
        this.evaluationStudentRepository = evaluationStudentRepository;
    }

    //TODO: Convertire con UUID
    public List<EvaluationStudent> findByEvaluationId(Long evaluationId){
        return evaluationStudentRepository.findByEvaluationId(evaluationId);
    }

    public EvaluationStudent save(EvaluationStudent evaluationStudent) {
        return evaluationStudentRepository.save(evaluationStudent);
    }

}