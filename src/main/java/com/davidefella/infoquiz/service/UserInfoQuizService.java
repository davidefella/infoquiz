package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import com.davidefella.infoquiz.repository.UserInfoQuizRepository;
import com.davidefella.infoquiz.utility.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserInfoQuizService {

    private final UserInfoQuizRepository userInfoQuizRepository;

    @Autowired
    public UserInfoQuizService(UserInfoQuizRepository userInfoQuizRepository) {
        this.userInfoQuizRepository = userInfoQuizRepository;
    }

    public List<UserInfoQuiz> saveAll(List<UserInfoQuiz> usersInfoQuiz) {
        log.info("{} Inizio salvataggio di {} utenti", LogConstants.LOG_TAG_INFOQUIZ, usersInfoQuiz.size());
        List<UserInfoQuiz> savedUsers = userInfoQuizRepository.saveAll(usersInfoQuiz);
        log.info("{} Salvataggio completato di {} utenti", LogConstants.LOG_TAG_INFOQUIZ, savedUsers.size());
        return savedUsers;
    }

    public Optional<UserInfoQuiz> findByUUID(UUID uuid) {
        log.info("{} Ricerca dell'utente con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        Optional<UserInfoQuiz> user = userInfoQuizRepository.findByUuid(uuid);
        if (user.isPresent()) {
            log.info("{} Utente trovato con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        } else {
            log.warn("{} Nessun utente trovato con UUID: {}", LogConstants.LOG_TAG_INFOQUIZ, uuid);
        }
        return user;
    }

    public List<Student> findAllStudents() {
        log.info("{} Ricerca di tutti gli studenti", LogConstants.LOG_TAG_INFOQUIZ);
        List<Student> students = userInfoQuizRepository.findByInfoQuizRole(InfoQuizRole.STUDENT);
        log.info("{} Trovati {} studenti", LogConstants.LOG_TAG_INFOQUIZ, students.size());
        return students;
    }

    public Optional<Teacher> findTeacherByEmail(String email) {
        log.info("{} Ricerca dell'insegnante con email: {}", LogConstants.LOG_TAG_INFOQUIZ, email);
        Optional<UserInfoQuiz> userInfoQuizOpt = userInfoQuizRepository.findByEmail(email);
        Optional<Teacher> teacher = convertToTeacher(userInfoQuizOpt);
        if (teacher.isPresent()) {
            log.info("{} Insegnante trovato con email: {}", LogConstants.LOG_TAG_INFOQUIZ, email);
        } else {
            log.warn("{} Nessun insegnante trovato con email: {}", LogConstants.LOG_TAG_INFOQUIZ, email);
        }
        return teacher;
    }

    public Optional<Student> findStudentByUUID(UUID uuid) {
        Optional<UserInfoQuiz> userInfoQuizOpt = userInfoQuizRepository.findByUuid(uuid);

        return convertToStudent(userInfoQuizOpt);
    }


    public Optional<Teacher> findTeacherByUUID(UUID uuid) {
        Optional<UserInfoQuiz> userInfoQuizOpt = userInfoQuizRepository.findByUuid(uuid);

        return convertToTeacher(userInfoQuizOpt);
    }

    public Optional<UserInfoQuiz> findByEmail(String email) {
        return userInfoQuizRepository.findByEmail(email);
    }

    private Optional<Teacher> convertToTeacher(Optional<UserInfoQuiz> userInfoQuizOpt){
        if (userInfoQuizOpt.isPresent() && userInfoQuizOpt.get() instanceof Teacher) {
            return Optional.of((Teacher) userInfoQuizOpt.get());
        }
        return Optional.empty();
    }

    private Optional<Student> convertToStudent(Optional<UserInfoQuiz> userInfoQuizOpt){
        if (userInfoQuizOpt.isPresent() && userInfoQuizOpt.get() instanceof Student) {
            return Optional.of((Student) userInfoQuizOpt.get());
        }

        return Optional.empty();

    }
}