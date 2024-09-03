package com.davidefella.infoquiz.model.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_item_id", nullable = false)
    private Question question;

    @JoinColumn(nullable = false)
    private String answerText;
    private boolean isCorrect;

    public Answer(String code, Question question, String answerText, boolean isCorrect) {
        this.question = question;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
        this.code = code;
    }
}
