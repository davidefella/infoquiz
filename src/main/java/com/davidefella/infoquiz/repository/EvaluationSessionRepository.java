package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.EvaluationSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EvaluationSessionRepository extends JpaRepository<EvaluationSession, Long> {

    Optional<EvaluationSession> findByUuid(UUID uuid);
    List<EvaluationSession> findByEvaluationUuid(UUID uuid);

}
