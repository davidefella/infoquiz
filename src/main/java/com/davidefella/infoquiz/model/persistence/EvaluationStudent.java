package com.davidefella.infoquiz.model.persistence;

import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rappresenta la tabella di join tra entità Evaluation e Student
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserInfoQuiz student;

    private double score;

    public EvaluationStudent(Evaluation evaluation, UserInfoQuiz student, double score) {
        this.evaluation = evaluation;
        this.student = student;
        this.score = score;
    }

}