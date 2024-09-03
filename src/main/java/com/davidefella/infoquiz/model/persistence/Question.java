package com.davidefella.infoquiz.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Evaluation evaluation;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Answer> answers;

    @JoinColumn(nullable = false)
    private String questionText;

    public Question(String code, Evaluation evaluation, String questionText ) {
        this.code = code;
        this.evaluation = evaluation;
        this.questionText = questionText;

    }
}