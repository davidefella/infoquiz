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
    private EvaluationSession session;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private double score;

    public EvaluationStudent(EvaluationSession session, Student student, double score) {
        this.uuid = UUID.randomUUID();
        this.session = session;
        this.student = student;
        this.score = score;
    }

    public EvaluationStudent(UUID uuid, EvaluationSession session, Student student, double score) {
        this.uuid = uuid;
        this.session = session;
        this.student = student;
        this.score = score;
    }
}