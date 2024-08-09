package com.davidefella.infoquiz.model.persistence;

import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La singola domanda associata al quiz. Una domanda può essere associata
 * ad un solo quiz, un quiz è naturalmente composto da tante domande.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String code; // Alternativo a id del DB
    private String questionText;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Evaluation evaluation;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> answers;

    public Question(String code, Evaluation evaluation, String questionText ) {
        this.evaluation = evaluation;
        this.questionText = questionText;
        this.code = code;
    }
}