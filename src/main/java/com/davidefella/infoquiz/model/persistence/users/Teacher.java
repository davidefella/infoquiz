package com.davidefella.infoquiz.model.persistence.users;

import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Teacher extends UserInfoQuiz{

    @OneToMany(mappedBy = "assignedTeacher")
    private List<Evaluation> evaluationsAssigned;

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.EAGER)
    private List<Classroom> classrooms;

    public Teacher(String lastName, String firstName, String email, String password) {
        super(lastName, firstName, email, password, InfoQuizRole.ROLE_TEACHER);
    }

    public Teacher(UUID uuid, String lastName, String firstName, String email, String password) {
        super(uuid, lastName, firstName, email, password, InfoQuizRole.ROLE_TEACHER);
    }
}
