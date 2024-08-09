package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.EvaluationPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationPlayerRepository extends JpaRepository<EvaluationPlayer, Long> {
}

