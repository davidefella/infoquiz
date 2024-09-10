package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    Optional<Classroom> findByUuid(UUID uuid);

    List<Classroom> findByTeachers(Teacher teacher);
}