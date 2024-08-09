package com.davidefella.infoquiz.model.web;

import com.davidefella.infoquiz.model.persistence.Answer;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
* Classe che mappa la sessione dati che serve tra una pagina e l'altra.
* Non ha reali dati aggiuntivi, è un collettore di oggetti.
*
* TODO: Rendere classe Serializzabile
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizSessionData {

    private Player player;
    private Evaluation evaluation;
    private List<Answer> answers = new ArrayList<>();
    private QuizResult quizResult;

    public QuizSessionData(Player player, Evaluation evaluation) {
        this.player = player;
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return String.format("QuizSessionData{evaluationId=%d, player=%s %s, answers=%s}",
                evaluation.getId(),
                player.getFirstName(),
                player.getLastName(),
                answers);
    }

}
