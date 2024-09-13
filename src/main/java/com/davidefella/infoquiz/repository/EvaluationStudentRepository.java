package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.EvaluationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationStudentRepository extends JpaRepository<EvaluationStudent, Long> {

}

