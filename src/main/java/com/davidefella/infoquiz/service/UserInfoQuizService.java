package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import com.davidefella.infoquiz.repository.UserInfoQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoQuizService {

    private final UserInfoQuizRepository userInfoQuizRepository;

    @Autowired
    public UserInfoQuizService(UserInfoQuizRepository userInfoQuizRepository) {
        this.userInfoQuizRepository = userInfoQuizRepository;
    }

    public List<UserInfoQuiz> saveAll(List<UserInfoQuiz> usersInfoQuiz) {
        return userInfoQuizRepository.saveAll(usersInfoQuiz);
    }

    public Optional<UserInfoQuiz> findByUUID(UUID uuid) {
        return userInfoQuizRepository.findByUuid(uuid);
    }

    public List<Student> findAllStudents() {
        return userInfoQuizRepository.findByInfoQuizRole(InfoQuizRole.STUDENT);
    }

    public Optional<Teacher> findTeacherByEmail(String email) {
        Optional<UserInfoQuiz> userInfoQuizOpt = userInfoQuizRepository.findByEmail(email);

        if (userInfoQuizOpt.isPresent() && userInfoQuizOpt.get() instanceof Teacher) {
            return Optional.of((Teacher) userInfoQuizOpt.get());
        }

        return Optional.empty();
    }

    public Optional<UserInfoQuiz> findByEmail(String email) {
        return userInfoQuizRepository.findByEmail(email);
    }


}
