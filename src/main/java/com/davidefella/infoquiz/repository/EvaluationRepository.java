package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Optional<Evaluation> findByUuid(UUID uuid);

    /*TODO: Refactor*/
    List<Evaluation> findByAssignedTeacher(Teacher assignedTeacher);

    List<Evaluation> findByAssignedTeacherEmail(String email);

}

