package com.davidefella.infoquiz.model.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_item_id", nullable = false)
    private Question question;

    @JoinColumn(nullable = false)
    private String answerText;

    private boolean isCorrect;

    public Answer(Question question, String answerText, boolean isCorrect) {
        this.uuid = UUID.randomUUID();
        this.question = question;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    public Answer(UUID uuid, Question question, String answerText, boolean isCorrect) {
        this.uuid = uuid;
        this.question = question;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }
}
