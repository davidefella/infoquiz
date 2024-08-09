package com.davidefella.infoquiz;

import com.davidefella.infoquiz.model.persistence.*;
import com.davidefella.infoquiz.model.web.QuizResult;
import com.davidefella.infoquiz.model.web.QuizSessionData;
import com.davidefella.infoquiz.service.AnswerService;
import com.davidefella.infoquiz.service.EvaluationService;
import com.davidefella.infoquiz.service.PlayerService;
import com.davidefella.infoquiz.service.QuestionService;
import com.davidefella.infoquiz.service.business.QuizHandler;
import com.davidefella.infoquiz.utility.scoresettings.ScoreConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
* Nota, i test si basano sulla classe "DummyDataFactory" e sui dati che carica nel database.
*
* TODO: usare Mockito e dati svincolati dal DymmyDataFactory
* */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class QuizHandlerTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuizHandler quizHandler;

    @Autowired
    private ScoreConfiguration scoreConfiguration;


    @Test
    void testQuizAllCorrectAnswers() {
        // Arrange: Setup di tutto il necessario per il test
        Player player = new Player("Nome 3", "Cognome 3");
        Evaluation evaluation = evaluationService.findByCode("E1");
        List<Answer> correctAnswers = answerService.getAllCorrectAnswerByEvaluationID(evaluation.getId());

        QuizSessionData quizSessionData = new QuizSessionData();
        quizSessionData.setEvaluation(evaluation);
        quizSessionData.setPlayer(player);
        quizSessionData.setAnswers(correctAnswers);

        double expectedScore = scoreConfiguration.getBonusPoints() * questionService.findByEvaluationId(evaluation.getId()).size();

        // Act: Esegui l'azione che vuoi testare
        quizHandler.saveQuizResults(quizSessionData);
        QuizResult quizResult = quizSessionData.getQuizResult();

        // Assert: Verifica i risultati
        assertNotNull(quizResult, "Il risultato del quiz non dovrebbe essere nullo");
        assertEquals(expectedScore, quizResult.getFinalScore(), "Il punteggio dovrebbe essere massimo per tutte le risposte corrette");
    }

    // 0.0 come punteggio
    @Test
    void testQuizNoAnswers() {
        // Arrange
        Player p = new Player("Nome", "Cognome");
        Evaluation e1 = evaluationService.findByCode("E1");

        List<Answer> noAnswers = new ArrayList<>(); // Empty list for no answers

        QuizSessionData quizSessionData = new QuizSessionData();
        quizSessionData.setEvaluation(e1);
        quizSessionData.setPlayer(p);
        quizSessionData.setAnswers(noAnswers);

        // Act
        quizHandler.saveQuizResults(quizSessionData);
        QuizResult qr = quizSessionData.getQuizResult();

        // Assert
        assertNotNull(qr);
        assertEquals(0.0, qr.getFinalScore(), "Score should be zero for no answers");
    }

    @Test
    void testQuizMixedAnswers() {
        // Arrange
        Player player = new Player("Nome", "Cognome");
        Evaluation evaluation = evaluationService.findByCode("E1");

        // Retrieve questions and their correct answers
        List<Question> questions = questionService.findByEvaluationId(evaluation.getId());
        List<Answer> mixedAnswers = new ArrayList<>();


        // Assume the evaluation has at least three questions
        if (questions.size() >= 3) {

            // For the first question, use the correct answer
            Answer correctAnswer = questions.get(0).getAnswers().stream()
                    .filter(Answer::isCorrect)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No correct answer found"));

            // For the second question, use an incorrect answer
            Answer incorrectAnswer = questions.get(1).getAnswers().stream()
                    .filter(answer -> !answer.isCorrect())
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No incorrect answer found"));

            // For the third question, leave it unanswered (null represents unanswered in this context)

            mixedAnswers.add(correctAnswer);
            mixedAnswers.add(incorrectAnswer);
            mixedAnswers.add(null); // No answer for the third question
        }

        QuizSessionData quizSessionData = new QuizSessionData();
        quizSessionData.setEvaluation(evaluation);
        quizSessionData.setPlayer(player);
        quizSessionData.setAnswers(mixedAnswers);

        // Calculate expected score
        double expectedScore = scoreConfiguration.getBonusPoints() - scoreConfiguration.getPenaltyPoints() + scoreConfiguration.getNotAnsweredPoints();

        // Act
        quizHandler.saveQuizResults(quizSessionData);
        QuizResult quizResult = quizSessionData.getQuizResult();

        // Assert
        assertNotNull(quizResult, "Il risultato del quiz non dovrebbe essere nullo");
        assertEquals(expectedScore, quizResult.getFinalScore(), "Il punteggio dovrebbe riflettere una risposta corretta, una sbagliata e una non data");
    }

}
