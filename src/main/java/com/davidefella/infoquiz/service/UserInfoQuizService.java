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

    private UserInfoQuizRepository userInfoQuizRepository;

    @Autowired
    public UserInfoQuizService(UserInfoQuizRepository userInfoQuizRepository) {
        this.userInfoQuizRepository = userInfoQuizRepository;
    }

    public List<UserInfoQuiz> findAll() {
        return userInfoQuizRepository.findAll();
    }

    public Optional<UserInfoQuiz> findById(Long id) {
        return userInfoQuizRepository.findById(id);
    }

    public Optional<UserInfoQuiz> findByUUID(UUID uuid) {
        return userInfoQuizRepository.findByUuid(uuid);
    }

    public List<Student> findAllStudents() {
        return userInfoQuizRepository.findByInfoQuizRole(InfoQuizRole.STUDENT);
    }

    public Optional<Teacher> findTeacherByEmail(String email) {
        Optional<UserInfoQuiz> user = userInfoQuizRepository.findByEmail(email);

        if (user.isPresent() && user.get() instanceof Teacher) {
            return Optional.of((Teacher) user.get());
        }
        return Optional.empty();
    }

    public Optional<UserInfoQuiz> findByEmail(String email) {
        return userInfoQuizRepository.findByEmail(email);
    }

    public Optional<Teacher> findTeacherByLastNameAndFirstName(String lastName, String firstName) {
        Optional<UserInfoQuiz> userInfoQuizOpt = userInfoQuizRepository.findByLastNameAndFirstName(lastName, firstName);

        Optional<UserInfoQuiz> userTmp = findUserByLastNameAndFirstName(lastName, firstName);

        if (userTmp.isEmpty() || !(userTmp.get() instanceof Teacher ))
            return Optional.empty();

        return Optional.of((Teacher) userTmp.get());
    }

    public Optional<Student> findStudentByLastNameAndFirstName(String lastName, String firstName) {

        Optional<UserInfoQuiz> userTmp = findUserByLastNameAndFirstName(lastName, firstName);

        if (userTmp.isEmpty() || !(userTmp.get() instanceof Student ))
            return Optional.empty();

        return Optional.of((Student) userTmp.get());
    }

    private Optional<UserInfoQuiz> findUserByLastNameAndFirstName(String lastName, String firstName){
        Optional<UserInfoQuiz> userInfoQuizOpt = userInfoQuizRepository.findByLastNameAndFirstName(lastName, firstName);

        if (userInfoQuizOpt.isEmpty()) {
            return Optional.empty();
        }

        return userInfoQuizOpt;
    }

    /*
     * TODO: Add check fields
     * */
    public List<UserInfoQuiz> saveAll(List<UserInfoQuiz> usersInfoQuiz) {
        return userInfoQuizRepository.saveAll(usersInfoQuiz);
    }
}
