package com.davidefella.infoquiz.controller.web.thymeleaf;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.Player;
import com.davidefella.infoquiz.model.web.QuizSessionData;
import com.davidefella.infoquiz.service.EvaluationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class HomeController {

    private EvaluationService evaluationService;

    @Autowired
    public HomeController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/")
    public String showForm(Model model) {

        /* quizSessionData memorizza le informazioni da condividere tra le varie pagine,
        non ha una persistenza a DB */
        QuizSessionData quizSessionData = new QuizSessionData(new Player(), new Evaluation());

        /* i model servono per prendere i dati lato thymeleaf*/
        model.addAttribute("quizSessionData", quizSessionData);
        model.addAttribute("evaluations", evaluationService.findAllActiveEvaluations());

        // qui indico il nome del template
        return "home";
    }

    @PostMapping("/evaluations/start")
    public String submitPlayerForm(@ModelAttribute QuizSessionData quizSessionData, HttpSession session) {
        Optional<Evaluation> evaluationOpt = evaluationService.findById(quizSessionData.getEvaluation().getId());

        if (evaluationOpt.isEmpty()) {
            return "redirect:/";
        }

        // Voglio portare le informazioni della valutazione in corso alla prossima pagina
        quizSessionData.setEvaluation(evaluationOpt.get());

        session.setAttribute("quizSessionData", quizSessionData);

        return "redirect:/quiz";
    }
}
