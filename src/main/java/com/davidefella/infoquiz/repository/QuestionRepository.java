package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByEvaluationId(Long evaluationId);

    Optional<Question> findByUuid(UUID uuid);

    Optional<List<Question>> findByEvaluationUuid(UUID evaluationCode);

    void deleteById(Long id);
}
