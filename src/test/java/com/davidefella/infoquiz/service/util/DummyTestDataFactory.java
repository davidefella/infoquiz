package com.davidefella.infoquiz.service.util;

import com.davidefella.infoquiz.model.persistence.*;
import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.service.*;
import com.davidefella.infoquiz.service.business.EvaluationHandler;
import com.davidefella.infoquiz.utility.StartupDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;


@Component
public class DummyTestDataFactory {

    private static final Logger logger = LoggerFactory.getLogger(StartupDataLoader.class);

    private final AnswerService answerService;
    private final EvaluationService evaluationService;
    private final EvaluationStudentService evaluationStudentService;
    private final UserInfoQuizService userInfoQuizService;

    private final QuestionService questionItemService;
    private final EvaluationHandler evaluationHandler;

    @Autowired
    public DummyTestDataFactory(AnswerService answerService, EvaluationService evaluationService,
                                EvaluationStudentService evaluationStudentService, UserInfoQuizService userInfoQuizService,
                                QuestionService questionItemService, EvaluationHandler evaluationHandler) {
        this.answerService = answerService;
        this.evaluationService = evaluationService;
        this.evaluationStudentService = evaluationStudentService;
        this.userInfoQuizService = userInfoQuizService;
        this.questionItemService = questionItemService;
        this.evaluationHandler = evaluationHandler;
    }

    public void loadAllDummyData() {
        loadUserData();
        loadEvaluationData();
        loadQuestionData();
        loadAnswerData();
    }

    public void loadUserData() {
        List<UserInfoQuiz> userInfoQuizs = new ArrayList<>(List.of(new Student(UUIDRegistry.STUDENT_1, "T_Cognome 1", "T_Nome 1", "T_s1@email.com")));
        userInfoQuizService.saveAll(userInfoQuizs);
        logger.info("Loaded Students");
    }

    private void loadEvaluationData() {
        List<Evaluation> evaluations = List.of(
                new Evaluation(UUIDRegistry.EVALUATION_1, "Evaluation 1", LocalDate.now(), "Evaluation per la classe di Test", null, true),
                new Evaluation(UUIDRegistry.EVALUATION_2, "Evaluation 2", LocalDate.now(), "Evaluation vuota", null, true)
        );

        evaluationService.saveAll(evaluations);
        logger.info("Loaded Evaluations");
    }

    private void loadQuestionData() {
        Evaluation e1 = evaluationService.findByUUID(UUIDRegistry.EVALUATION_1).get();

        List<Question> questions = Arrays.asList(
                new Question(UUIDRegistry.QUESTION_1, e1, "Question 1 ?"),
                new Question(UUIDRegistry.QUESTION_2, e1, "Question 2 ?"),
                new Question(UUIDRegistry.QUESTION_3, e1, "Question 3 ?")
        );

        questionItemService.saveAll(questions);
        logger.info("Loaded Questions");
    }

    private void loadAnswerData() {
        Question q1 = questionItemService.findByUUID(UUIDRegistry.QUESTION_1).get();

        List<Answer> answersQ1 = Arrays.asList(
                new Answer(UUIDRegistry.ANSWER_1_Q1, q1, "Correct answer", true),
                new Answer(UUIDRegistry.ANSWER_2_Q1, q1, "Wrong answer", false),
                new Answer(UUIDRegistry.ANSWER_3_Q1, q1, "Wrong answer", false)
        );
        answerService.saveAll(answersQ1);

        List<Answer> answersQ2 = Arrays.asList(
                new Answer(UUIDRegistry.ANSWER_1_Q2, q1, "Wrong answer", false),
                new Answer(UUIDRegistry.ANSWER_2_Q2, q1, "Correct answer", true),
                new Answer(UUIDRegistry.ANSWER_3_Q2, q1, "Wrong answer", false)
        );
        answerService.saveAll(answersQ2);

        List<Answer> answersQ3 = Arrays.asList(
                new Answer(UUIDRegistry.ANSWER_1_Q3, q1, "Wrong answer", false),
                new Answer(UUIDRegistry.ANSWER_2_Q3, q1, "Wrong answer", false),
                new Answer(UUIDRegistry.ANSWER_3_Q3, q1, "Correct answer", true)
        );
        answerService.saveAll(answersQ3);

        logger.info("Loaded answers");
    }
}
