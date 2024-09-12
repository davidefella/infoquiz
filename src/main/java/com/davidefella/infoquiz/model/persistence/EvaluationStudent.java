package com.davidefella.infoquiz.model.persistence;

import com.davidefella.infoquiz.model.persistence.users.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@Builder
public class EvaluationStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private double score;

    public EvaluationStudent(Evaluation evaluation, Student student, double score) {
        this.uuid = UUID.randomUUID();
        this.evaluation = evaluation;
        this.student = student;
        this.score = score;
    }

    public EvaluationStudent(UUID uuid, Evaluation evaluation, Student student, double score) {
        this.uuid = uuid;
        this.evaluation = evaluation;
        this.student = student;
        this.score = score;
    }
}