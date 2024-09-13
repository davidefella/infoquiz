package com.davidefella.infoquiz.model.persistence;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Evaluation evaluation;

    @JoinColumn(nullable = false)
    private String questionText;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Question(Evaluation evaluation, String questionText ) {
        this.uuid = UUID.randomUUID();
        this.evaluation = evaluation;
        this.questionText = questionText;
    }

    public Question(UUID uuid, Evaluation evaluation, String questionText ) {
        this.uuid = uuid;
        this.evaluation = evaluation;
        this.questionText = questionText;
    }
}