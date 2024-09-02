package com.davidefella.infoquiz.model.persistence.users;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Teacher extends UserInfoQuiz{

    @ElementCollection
    private List<String> subjects;

    @OneToMany(mappedBy = "assignedTeacher")
    private List<Evaluation> evaluationsAssigned;


    public Teacher(String code, String lastName, String firstName, String email, List<String> subjects) {
        super(code, lastName, firstName, email, InfoQuizRole.TEACHER);
        this.subjects = subjects;
    }
}
