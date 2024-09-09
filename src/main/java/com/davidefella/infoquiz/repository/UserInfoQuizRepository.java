package com.davidefella.infoquiz.repository;

import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserInfoQuizRepository extends JpaRepository<UserInfoQuiz, Long> {

    Optional<UserInfoQuiz> findByLastNameAndFirstName(String lastName, String firstName);

    List<Student> findByInfoQuizRole(InfoQuizRole role);

    Optional<UserInfoQuiz> findByEmail(String email);

    Optional<UserInfoQuiz> findByUuid(UUID uuid);

}
