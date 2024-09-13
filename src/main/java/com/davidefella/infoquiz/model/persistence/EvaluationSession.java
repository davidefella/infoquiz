package com.davidefella.infoquiz.model.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private UUID uuid;

    private LocalDate sessionDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationSessionStatus status = EvaluationSessionStatus.STANDBY;

    @ManyToOne
    private Evaluation evaluation;

    @ManyToOne
    private Classroom classroom;

    public EvaluationSession(LocalDate sessionDate, LocalTime startTime, LocalTime endTime, EvaluationSessionStatus status, Evaluation evaluation, Classroom classroom) {
        this.uuid = UUID.randomUUID();
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.evaluation = evaluation;
        this.classroom = classroom;
    }

    public EvaluationSession(UUID uuid, LocalDate sessionDate, LocalTime startTime, LocalTime endTime, EvaluationSessionStatus status, Evaluation evaluation, Classroom classroom) {
        this.uuid = uuid;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.evaluation = evaluation;
        this.classroom = classroom;
    }
}