package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findByIsActiveTrue();

    Evaluation findByCode(String code);
    
}

