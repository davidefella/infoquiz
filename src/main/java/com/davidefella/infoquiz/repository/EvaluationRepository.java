package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findByIsActiveTrue();

    Optional<Evaluation> findByUuid(UUID uuid);
}

