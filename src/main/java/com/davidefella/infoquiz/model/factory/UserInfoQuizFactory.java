package com.davidefella.infoquiz.model.factory;

import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;

import java.util.List;

public class UserInfoQuizFactory {

    private UserInfoQuizFactory(){}

    public static UserInfoQuiz createUser(String firstName, String lastName, String email, List<String> subjects, InfoQuizRole role, String password) {
        switch (role) {
            case STUDENT:
                return new Student(lastName, firstName, email, password);
            case TEACHER:
                return new Teacher(lastName, firstName, email, password, subjects);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
