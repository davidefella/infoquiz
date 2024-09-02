package com.davidefella.infoquiz.model.persistence.users;

import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* Entità del giocatore
* */
@Data
@Entity
@NoArgsConstructor
public class Student extends UserInfoQuiz {

    public Student(String code, String lastName, String firstName, String email) {
        super(code, lastName, firstName, email, InfoQuizRole.STUDENT);
    }

    public Student(String lastName, String firstName, String email) {
        super(lastName, firstName, email, InfoQuizRole.STUDENT);
    }
}