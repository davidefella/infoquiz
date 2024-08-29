package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByEvaluationId(Long evaluationId);

    Optional<Question> findByCode(String code);

    Optional<List<Question>> findByEvaluationCode(String evaluationCode);

    void deleteById(Long id);
}
