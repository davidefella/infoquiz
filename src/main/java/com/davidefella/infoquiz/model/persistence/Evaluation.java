package com.davidefella.infoquiz.model.persistence;

import com.davidefella.infoquiz.model.persistence.users.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private UUID uuid;

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

    @OneToMany(mappedBy = "evaluation")
    private List<Question> questions;

    public Evaluation(String title, LocalDate evaluationDate, String description, Teacher assignedTeacher, boolean isActive) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.evaluationDate = evaluationDate;
        this.description = description;
        this.assignedTeacher = assignedTeacher;
        this.isActive = isActive;
    }

    public Evaluation(UUID uuid, String title, LocalDate evaluationDate, String description, Teacher assignedTeacher, boolean isActive) {
        this.uuid = uuid;
        this.title = title;
        this.evaluationDate = evaluationDate;
        this.description = description;
        this.assignedTeacher = assignedTeacher;
        this.isActive = isActive;
    }
}