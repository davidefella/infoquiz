package com.davidefella.infoquiz.model.persistence.users;

import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
* Entità del giocatore
* */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends UserInfoQuiz {

    public Student(String lastName, String firstName, String email) {
        super(lastName, firstName, email, InfoQuizRole.STUDENT);
    }

    public Student(UUID uuid, String lastName, String firstName, String email) {
        super(uuid, lastName, firstName, email, InfoQuizRole.STUDENT);
    }
}