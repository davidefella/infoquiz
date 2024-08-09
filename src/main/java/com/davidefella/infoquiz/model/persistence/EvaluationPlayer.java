package com.davidefella.infoquiz.model.persistence;

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
 * Rappresenta la tabella di join tra entità Evaluation e Player
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private double score;

    public EvaluationPlayer(Evaluation evaluation, Player player, double score) {
        this.evaluation = evaluation;
        this.player = player;
        this.score = score;
    }

}