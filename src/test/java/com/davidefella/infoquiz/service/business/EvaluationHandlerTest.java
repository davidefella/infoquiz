package com.davidefella.infoquiz.service.business;

import com.davidefella.infoquiz.model.persistence.Answer;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.web.EvaluationResult;
import com.davidefella.infoquiz.service.AnswerService;
import com.davidefella.infoquiz.service.EvaluationService;
import com.davidefella.infoquiz.service.QuestionService;
import com.davidefella.infoquiz.service.UserInfoQuizService;
import com.davidefella.infoquiz.service.util.DummyTestDataFactory;
import com.davidefella.infoquiz.utility.scoresettings.ScoreConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/*
* Note: The tests are based on the "DummyDataFactory" class and the data it
* loads into the database
** */
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class EvaluationHandlerTest {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private UserInfoQuizService userInfoQuizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private EvaluationHandler evaluationHandler;

    @Autowired
    private ScoreConfiguration scoreConfiguration;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }

    @Test
    void testAllAnswersWrong(){
        // Arrange
        Evaluation ev1 = evaluationService.findByCode("T_E1").orElseThrow(() -> new NoSuchElementException("Evaluation not found"));
        Student st1 = userInfoQuizService.findStudentByLastNameAndFirstName("T_Cognome 1", "T_Nome 1").orElseThrow(() -> new NoSuchElementException("Student not found"));

        Answer a3 = answerService.findByCode("T_A3").orElseThrow(() -> new NoSuchElementException("T_A3 not found"));
        Answer a4 = answerService.findByCode("T_A4").orElseThrow(() -> new NoSuchElementException("T_A4 not found"));
        Answer a8 = answerService.findByCode("T_A8").orElseThrow(() -> new NoSuchElementException("T_A8 not found"));

        List<Answer> wrongAnswers = List.of(a3,a4,a8);

        // Act
        EvaluationResult evRes = evaluationHandler.saveEvaluationResults(ev1, st1, wrongAnswers);

        // Assert
        assertEquals(scoreConfiguration.getPenaltyPoints()*3, evRes.getFinalScore());
        assertEquals(0, evRes.getCorrectAnswers());
        assertEquals(0, evRes.getUnansweredQuestions());
        assertEquals(3, evRes.getWrongAnswers());

    }

    @Test
    void testAllAnswersCorrect(){
        // Arrange
        Evaluation ev1 = evaluationService.findByCode("T_E1").orElseThrow(() -> new NoSuchElementException("Evaluation not found"));
        Student st1 = userInfoQuizService.findStudentByLastNameAndFirstName("T_Cognome 1", "T_Nome 1").orElseThrow(() -> new NoSuchElementException("Student not found"));

        Answer a1 = answerService.findByCode("T_A1").orElseThrow(() -> new NoSuchElementException("T_A1 not found"));
        Answer a5 = answerService.findByCode("T_A5").orElseThrow(() -> new NoSuchElementException("T_A5 not found"));
        Answer a9 = answerService.findByCode("T_A9").orElseThrow(() -> new NoSuchElementException("T_A9 not found"));

        List<Answer> correctAnswers = List.of(a1,a5,a9);

        // Act
        EvaluationResult evRes = evaluationHandler.saveEvaluationResults(ev1, st1, correctAnswers);

        // Assert
        assertEquals(scoreConfiguration.getBonusPoints()*3, evRes.getFinalScore());
        assertEquals(3, evRes.getCorrectAnswers());
        assertEquals(0, evRes.getUnansweredQuestions());
        assertEquals(0, evRes.getWrongAnswers());

    }

    @Test
    void testMixedAnswers_1(){
        // Arrange
        Evaluation ev1 = evaluationService.findByCode("T_E1").orElseThrow(() -> new NoSuchElementException("Evaluation not found"));
        Student st1 = userInfoQuizService.findStudentByLastNameAndFirstName("T_Cognome 1", "T_Nome 1").orElseThrow(() -> new NoSuchElementException("stayer not found"));

        Answer a1 = answerService.findByCode("T_A1").orElseThrow(() -> new NoSuchElementException("T_A1 not found"));
        Answer a4 = answerService.findByCode("T_A4").orElseThrow(() -> new NoSuchElementException("T_A4 not found"));

        List<Answer> mixedAnswers = List.of(a1,a4);

        // Act
        EvaluationResult evRes = evaluationHandler.saveEvaluationResults(ev1, st1, mixedAnswers);

        // Assert
        assertEquals(scoreConfiguration.getBonusPoints() + scoreConfiguration.getPenaltyPoints() + scoreConfiguration.getNotAnsweredPoints(), evRes.getFinalScore());
        assertEquals(1, evRes.getCorrectAnswers());
        assertEquals(1, evRes.getUnansweredQuestions());
        assertEquals(1, evRes.getWrongAnswers());

    }

    @Test
    void testMixedAnswers_2(){
        // Arrange
        Evaluation ev1 = evaluationService.findByCode("T_E1").orElseThrow(() -> new NoSuchElementException("Evaluation not found"));
        Student st1 = userInfoQuizService.findStudentByLastNameAndFirstName("T_Cognome 1", "T_Nome 1").orElseThrow(() -> new NoSuchElementException("stayer not found"));

        Answer a2 = answerService.findByCode("T_A2").orElseThrow(() -> new NoSuchElementException("T_A2 not found"));
        Answer a9 = answerService.findByCode("T_A9").orElseThrow(() -> new NoSuchElementException("T_A9 not found"));

        List<Answer> mixedAnswers = List.of(a2,a9);

        // Act
        EvaluationResult evRes = evaluationHandler.saveEvaluationResults(ev1, st1, mixedAnswers);

        // Assert
        assertEquals(scoreConfiguration.getBonusPoints() + scoreConfiguration.getPenaltyPoints() + scoreConfiguration.getNotAnsweredPoints(), evRes.getFinalScore());
        assertEquals(1, evRes.getCorrectAnswers());
        assertEquals(1, evRes.getUnansweredQuestions());
        assertEquals(1, evRes.getWrongAnswers());

    }

    @Test
    void testAllUnanswered(){
        // Arrange
        Evaluation ev1 = evaluationService.findByCode("T_E1").orElseThrow(() -> new NoSuchElementException("Evaluation not found"));
        Student st1 = userInfoQuizService.findStudentByLastNameAndFirstName("T_Cognome 1", "T_Nome 1").orElseThrow(() -> new NoSuchElementException("stayer not found"));

        List<Answer> unanswered = new ArrayList<>();

        // Act
        EvaluationResult evRes = evaluationHandler.saveEvaluationResults(ev1, st1, unanswered);

        // Assert
        assertEquals(scoreConfiguration.getNotAnsweredPoints()*3, evRes.getFinalScore());
        assertEquals(0, evRes.getCorrectAnswers());
        assertEquals(3, evRes.getUnansweredQuestions());
        assertEquals(0, evRes.getWrongAnswers());

    }
}