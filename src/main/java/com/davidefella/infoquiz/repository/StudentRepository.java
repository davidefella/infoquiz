package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByLastNameAndFirstName(String lastName, String firstName);
}
