package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    //List<Answer> saveAll(List<Answer> answers);

    List<Answer> findByIsCorrectTrueAndQuestionEvaluationId(Long evaluationId);

    Optional<Answer> findByCode(String code);
}
