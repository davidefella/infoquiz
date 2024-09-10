package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByUuid(UUID uuid);

    //TODO: Rimuovere e passare oggetto Evaluation
    Optional<List<Question>> findByEvaluationUuid(UUID evaluationCode);

    //TODO: Rimuovere
    void deleteById(Long id);
}
