package com.davidefella.infoquiz.controller.web.thymeleaf;

import com.davidefella.infoquiz.model.persistence.Question;
import com.davidefella.infoquiz.model.web.QuizSessionData;
import com.davidefella.infoquiz.service.QuestionService;
import com.davidefella.infoquiz.service.business.QuizHandler;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class QuizController {

    private QuizHandler quizHandler;

    private QuestionService questionService;

    @Autowired
    public QuizController(QuizHandler quizHandler, QuestionService questionService) {
        this.quizHandler = quizHandler;
        this.questionService = questionService;
    }

    @GetMapping("/quiz")
    public String showQuizPage(Model model, HttpSession session) {
        QuizSessionData quizSessionData = (QuizSessionData) session.getAttribute("quizSessionData");

        if (quizSessionData == null) {
            return "redirect:/";
        }

        Long evaluationId = quizSessionData.getEvaluation().getId();
        List<Question> questions = questionService.findByEvaluationIdRandomized(evaluationId);

        model.addAttribute("questions", questions);
        model.addAttribute("quizSessionData", quizSessionData);

        return "quiz";
    }

    @PostMapping("/quiz/submit")
    public String submitQuiz(@ModelAttribute QuizSessionData quizSessionData, HttpSession session) {
        QuizSessionData sessionData = (QuizSessionData) session.getAttribute("quizSessionData");

        if (quizSessionData == null || sessionData == null) {
            return "redirect:/";
        }

        // Imposta nella sessione le risposte ricevute dal form
        sessionData.setAnswers(quizSessionData.getAnswers());

        // Logica per salvare le risposte e calcolare il punteggio
        quizHandler.saveQuizResults(sessionData);

        return "redirect:/results";
    }

}