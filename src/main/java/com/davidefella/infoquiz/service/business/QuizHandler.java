package com.davidefella.infoquiz.service.business;

import com.davidefella.infoquiz.model.persistence.*;
import com.davidefella.infoquiz.model.web.QuizResult;
import com.davidefella.infoquiz.model.web.QuizSessionData;
import com.davidefella.infoquiz.service.AnswerService;
import com.davidefella.infoquiz.service.EvaluationPlayerService;
import com.davidefella.infoquiz.service.EvaluationService;
import com.davidefella.infoquiz.service.PlayerService;
import com.davidefella.infoquiz.utility.scoresettings.ScoreConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuizHandler {

    private static final Logger logger = LoggerFactory.getLogger(QuizHandler.class);

    private EvaluationPlayerService evaluationPlayerService;

    private AnswerService answerService;

    private PlayerService playerService;

    private EvaluationService evaluationService;

    private ScoreConfiguration scoreConfiguration;

    @Autowired
    public QuizHandler(EvaluationPlayerService evaluationPlayerService, AnswerService answerService, PlayerService playerService, EvaluationService evaluationService, ScoreConfiguration scoreConfiguration) {
        this.evaluationPlayerService = evaluationPlayerService;
        this.answerService = answerService;
        this.playerService = playerService;
        this.evaluationService = evaluationService;
        this.scoreConfiguration = scoreConfiguration;
    }



    /**
     * Salva i risultati del quiz, inclusi i dati del giocatore e il punteggio.
     * Il metodo è void perchè ci pensa il Controller a gestire la sessione tra le varie pagine
     * @param quizSessionData i dati del quiz da salvare
     */
    @Transactional
    public void saveQuizResults(QuizSessionData quizSessionData) {

        // Il giocatore potrebbe non essere presente nel database. Se non presente, crealo.
        Optional<Player> storedPlayerOpt = playerService.findByFirstNameAndLastName(quizSessionData.getPlayer().getFirstName(),
                quizSessionData.getPlayer().getLastName());

        Player player = storedPlayerOpt.orElseGet(() -> {
            Player newPlayer = new Player(quizSessionData.getPlayer().getFirstName(), quizSessionData.getPlayer().getLastName());

            return playerService.save(newPlayer);
        });

        Evaluation evaluation = evaluationService.findById(quizSessionData.getEvaluation().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));


        QuizResult quizResult = calculateFinalResults(quizSessionData);
        quizSessionData.setQuizResult(quizResult);

        // Salva la entry nella tabella EvaluationPlayer (quella di Join)
        EvaluationPlayer evaluationPlayer = new EvaluationPlayer(evaluation, player, quizResult.getFinalScore());
        evaluationPlayerService.save(evaluationPlayer); // Ricorda è la tabella di join

    }

    /**
     * Calcola il punteggio del quiz.
     *
     * @param sessionData i dati del quiz
     * @return il punteggio calcolato
     */
    private QuizResult calculateFinalResults(QuizSessionData sessionData) {
        QuizResult quizResult = new QuizResult();

        List<Answer> answerList = sessionData.getAnswers();

        // Per ogni risposta data dal giocatore
        for (Answer answer : answerList) {

            if(answer == null)
                continue;

            Long answerId = answer.getId();

            if (answerId == null){
                quizResult.addUnansweredQuestion(scoreConfiguration.getNotAnsweredPoints());
                continue;
            }

            // Dal FE non ho le risposte, le devo ricercare una per una dal database
            Optional<Answer> storedAnswerOpt = answerService.findById(answerId);

            if (storedAnswerOpt.isPresent()) {
                Answer storedAnswer = storedAnswerOpt.get();

                if (storedAnswer.isCorrect()) {
                    quizResult.addCorrectAnswer(scoreConfiguration.getBonusPoints());
                } else {
                    quizResult.addWrongAnswer(scoreConfiguration.getPenaltyPoints());
                }
            } else {
                logger.warn("Answer {} not found, SessionData {}", answerId, sessionData);
                quizResult.addUnansweredQuestion(scoreConfiguration.getNotAnsweredPoints());
            }
        }

        return quizResult;
    }


}
