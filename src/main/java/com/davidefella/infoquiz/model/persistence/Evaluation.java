package com.davidefella.infoquiz.model.persistence;

import com.davidefella.infoquiz.model.persistence.users.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher assignedTeacher;

    @NotNull
    private LocalDate evaluationDate;

    @NotNull
    @Column(unique = true, nullable = false)
    private String title;

    private String description;

    private boolean isActive;

    public Evaluation( String code, String title, LocalDate evaluationDate, String description, Teacher assignedTeacher, boolean isActive) {
        this.code = code;
        this.title = title;
        this.evaluationDate = evaluationDate;
        this.description = description;
        this.assignedTeacher = assignedTeacher;
        this.isActive = isActive;
    }

}