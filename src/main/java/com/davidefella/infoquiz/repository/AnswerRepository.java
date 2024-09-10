package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByUuid(UUID uuid);
}