package com.davidefella.infoquiz.service.business;

import com.davidefella.infoquiz.model.persistence.*;
import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.model.web.EvaluationResult;
import com.davidefella.infoquiz.service.*;
import com.davidefella.infoquiz.utility.scoresettings.ScoreConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationHandler {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationHandler.class);

    private EvaluationStudentService evaluationStudentService;

    private AnswerService answerService;

    private UserInfoQuizService studentService;

    private EvaluationService evaluationService;

    private QuestionService questionService;

    private ScoreConfiguration scoreConfiguration;

    @Autowired
    public EvaluationHandler(EvaluationStudentService evaluationStudentService, AnswerService answerService, UserInfoQuizService studentService, EvaluationService evaluationService, QuestionService questionService, ScoreConfiguration scoreConfiguration) {
        this.evaluationStudentService = evaluationStudentService;
        this.answerService = answerService;
        this.studentService = studentService;
        this.evaluationService = evaluationService;
        this.questionService = questionService;
        this.scoreConfiguration = scoreConfiguration;
    }

    /**
     * Saves the results of the evaluation, including student data and score.
     * The method is void because the Controller manages the session across the various pages.
     */
    @Transactional
    public EvaluationResult saveEvaluationResults(Evaluation evaluationSession, UserInfoQuiz userInfoSession, List<Answer> answers) {

        Evaluation evaluation = evaluationService.findByCode(evaluationSession.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));

        EvaluationResult evaluationResult = calculateEvaluationScore(evaluation, answers);

        // Salva la entry nella tabella EvaluationStudent (quella di Join)
        EvaluationStudent evaluationStudent = new EvaluationStudent(evaluation, userInfoSession, evaluationResult.getFinalScore());
        evaluationStudentService.save(evaluationStudent); // Ricorda è la tabella di join

        return evaluationResult;
    }

    /**
     * Calculates the evaluation score.
     *
     * @param answers the evaluation data
     * @return the calculated score
     */
    private EvaluationResult calculateEvaluationScore(Evaluation evaluation, List<Answer> answers) {
        EvaluationResult evaluationResult = new EvaluationResult();

        Optional<List<Question>> questionsByEvaluationCodeOpt = questionService.findByEvaluationCode(evaluation.getCode());
        /* TODO: Lanciare una eccezione */
        if(questionsByEvaluationCodeOpt.isPresent()){
            int totalQuestions = questionsByEvaluationCodeOpt.get().size();
            evaluationResult.setUnansweredQuestions( (byte) (totalQuestions - answers.size()) );
        }

        for (Answer answer : answers) {
            Long answerId = answer.getId();

            /* TODO: Lanciare eccezione?*/
            if (answerId == null) {
                evaluationResult.addUnansweredQuestion(scoreConfiguration.getNotAnsweredPoints());
                continue;
            }

            // From the FE, I don't have the answers, I need to fetch them one by one from the database
            Optional<Answer> storedAnswerOpt = answerService.findById(answerId);
            if (storedAnswerOpt.isPresent()) {
                Answer storedAnswer = storedAnswerOpt.get();

                if (storedAnswer.isCorrect()) {
                    evaluationResult.addCorrectAnswer(scoreConfiguration.getBonusPoints());
                } else {
                    evaluationResult.addWrongAnswer(scoreConfiguration.getPenaltyPoints());
                }
            } else {
                logger.warn("Answer {} not found", answerId);

                /* TODO: Lanciare eccezione? */
                evaluationResult.addUnansweredQuestion(scoreConfiguration.getNotAnsweredPoints());
            }
        }

        return evaluationResult;
    }
}