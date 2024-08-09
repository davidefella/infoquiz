package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByEvaluationId(Long evaluationId);

}
