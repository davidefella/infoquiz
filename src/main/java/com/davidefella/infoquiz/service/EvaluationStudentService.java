package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.EvaluationStudent;
import com.davidefella.infoquiz.repository.EvaluationStudentRepository;
import com.davidefella.infoquiz.utility.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EvaluationStudentService {

    private final EvaluationStudentRepository evaluationStudentRepository;

    @Autowired
    public EvaluationStudentService(EvaluationStudentRepository evaluationStudentRepository) {
        this.evaluationStudentRepository = evaluationStudentRepository;
    }

    public EvaluationStudent save(EvaluationStudent evaluationStudent) {
        log.info("{} Inizio salvataggio di EvaluationStudent con ID: {}", LogConstants.LOG_TAG_INFOQUIZ, evaluationStudent.getId());
        EvaluationStudent savedStudent = evaluationStudentRepository.save(evaluationStudent);
        log.info("{} Salvataggio completato di EvaluationStudent con ID: {}", LogConstants.LOG_TAG_INFOQUIZ, savedStudent.getId());
        return savedStudent;
    }

    public List<EvaluationStudent> saveAll(List<EvaluationStudent> evaluationsStudents) {
        log.info("{} Inizio salvataggio di {} EvaluationStudents", LogConstants.LOG_TAG_INFOQUIZ, evaluationsStudents.size());
        List<EvaluationStudent> savedStudents = evaluationStudentRepository.saveAll(evaluationsStudents);
        log.info("{} Salvataggio completato di {} EvaluationStudents", LogConstants.LOG_TAG_INFOQUIZ, savedStudents.size());
        return savedStudents;
    }
}
