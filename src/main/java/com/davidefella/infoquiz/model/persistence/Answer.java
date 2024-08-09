package com.davidefella.infoquiz.model.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Risposta associata alla domanda (che è associata al quiz). Nella
 * versione attuale ho una sola domanda corretta per domanda e tutte
 * le risposte pesano allo stesso modo, in negativo e/o in positivo
 *  */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_item_id", nullable = false)
    private Question question;

    @Column(unique = true)
    private String code; // Alternativo a id del DB

    private String answerText;
    private boolean isCorrect;

    public Answer(String code, Question question, String answerText, boolean isCorrect) {
        this.question = question;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
        this.code = code;
    }

    public Long getQuestionId() {
        return question != null ? question.getId() : null;
    }
}
