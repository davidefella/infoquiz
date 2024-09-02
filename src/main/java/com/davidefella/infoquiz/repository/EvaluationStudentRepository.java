package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.EvaluationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationStudentRepository extends JpaRepository<EvaluationStudent, Long> {

    List<EvaluationStudent> findByEvaluationId(Long evaluationId);

}

