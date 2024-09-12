package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.EvaluationStudent;
import com.davidefella.infoquiz.repository.EvaluationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationStudentService {

    private final EvaluationStudentRepository evaluationStudentRepository;

    @Autowired
    public EvaluationStudentService(EvaluationStudentRepository evaluationStudentRepository) {
        this.evaluationStudentRepository = evaluationStudentRepository;
    }

    public EvaluationStudent save(EvaluationStudent evaluationStudent) {
        return evaluationStudentRepository.save(evaluationStudent);
    }

    public List<EvaluationStudent> saveAll(List<EvaluationStudent> evaluationsStudents) {
        return evaluationStudentRepository.saveAll(evaluationsStudents);
    }

}