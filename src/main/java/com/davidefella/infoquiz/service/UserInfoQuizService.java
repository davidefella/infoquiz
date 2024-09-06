package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.factory.UserInfoQuizFactory;
import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import com.davidefella.infoquiz.repository.UserInfoQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public List<Student> findAllStudents() {
        return userInfoQuizRepository.findByInfoQuizRole(InfoQuizRole.STUDENT);
    }

    public Optional<UserInfoQuiz> findByLastNameAndFirstName(String lastName, String firstName) {
        return userInfoQuizRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    public Optional<Student> findStudentByLastNameAndFirstName(String lastName, String firstName) {
        Optional<UserInfoQuiz> userInfoQuizOpt = userInfoQuizRepository.findByLastNameAndFirstName(lastName, firstName);

        if (userInfoQuizOpt.isEmpty()) {
            return Optional.empty();
        }

        UserInfoQuiz userTmp = userInfoQuizOpt.get();

        if (userTmp instanceof Student) {
            return Optional.of((Student) userTmp);
        } else {
            return Optional.empty();
        }
    }

    /*
     * TODO: Add check fields
     * */
    public List<UserInfoQuiz> saveAll(List<UserInfoQuiz> usersInfoQuiz) {
        return userInfoQuizRepository.saveAll(usersInfoQuiz);
    }


    public UserInfoQuiz save(String code, String lastName, String firstName, String email, List<String> subjects, InfoQuizRole role) {

        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("First name and last name cannot be null");
        }

        UserInfoQuiz userInfoQuiz = UserInfoQuizFactory.createUser(code, lastName, firstName, email, subjects, role);

        return userInfoQuizRepository.save(userInfoQuiz);
    }

    /* Utile per test e DataLoader */
    public UserInfoQuiz save(UserInfoQuiz userInfoQuiz) {
        return userInfoQuizRepository.save(userInfoQuiz);
    }

}
