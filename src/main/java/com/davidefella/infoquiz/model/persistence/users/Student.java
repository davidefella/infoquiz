package com.davidefella.infoquiz.model.persistence.users;

import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends UserInfoQuiz {

    public Student(String lastName, String firstName, String email, String password) {
        super(lastName, firstName, email, password, InfoQuizRole.STUDENT);
    }

    public Student(UUID uuid, String lastName, String firstName, String email, String password) {
        super(uuid, lastName, firstName, email, password, InfoQuizRole.STUDENT);
    }
}