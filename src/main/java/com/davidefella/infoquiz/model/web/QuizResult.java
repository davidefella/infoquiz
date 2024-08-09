package com.davidefella.infoquiz.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*
 * Modello che non è persistente, serve per passare all'ultima pagina un
 * riepilogo sul punteggio del giocatore
 * */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {

    private double finalScore;
    private byte wrongAnswers;
    private byte correctAnswers;
    private byte unansweredQuestions;

    public void addCorrectAnswer(float bonus) {
        correctAnswers++;
        updateFinalScore(bonus);
    }

    public void addWrongAnswer(float penalty) {
        wrongAnswers++;
        updateFinalScore(-penalty);
    }

    // By default risposte non date non tolgono punti
    public void addUnansweredQuestion(float amount) {
        unansweredQuestions++;
        updateFinalScore(amount);
    }

    private void updateFinalScore(float amount) {
        finalScore += amount;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "finalScore=" + finalScore +
                ", wrongAnswers=" + wrongAnswers +
                ", correctAnswers=" + correctAnswers +
                ", unansweredQuestions=" + unansweredQuestions +
                '}';
    }
}
