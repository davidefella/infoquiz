package com.davidefella.infoquiz.controller.web.thymeleaf;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.davidefella.infoquiz.model.web.QuizSessionData;
import com.davidefella.infoquiz.service.EvaluationService;

@Controller
public class ResultController {

    private EvaluationService evaluationService;

    @Autowired
    public ResultController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/results")
    public String showResults(HttpSession session, Model model) {
        QuizSessionData sessionData = (QuizSessionData) session.getAttribute("quizSessionData");

        if (sessionData == null) {
            return "redirect:/";
        }

        double maxScore = evaluationService.calculateMaxScore(sessionData.getEvaluation().getId());
        model.addAttribute("quizSessionData", sessionData);
        model.addAttribute("quizResult", sessionData.getQuizResult());
        model.addAttribute("maxScore", maxScore);

        return "results";
    }
}
