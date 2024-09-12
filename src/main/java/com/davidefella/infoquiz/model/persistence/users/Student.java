package com.davidefella.infoquiz.model.persistence.users;

import com.davidefella.infoquiz.model.persistence.Classroom;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Classroom classroom;

    public Student(String lastName, String firstName, String email, String password, Classroom classroom) {
        super(lastName, firstName, email, password, InfoQuizRole.STUDENT);
        this.classroom = classroom;
    }

    public Student(UUID uuid, String lastName, String firstName, String email, String password, Classroom classroom) {
        super(uuid, lastName, firstName, email, password, InfoQuizRole.STUDENT);
        this.classroom = classroom;
    }
}