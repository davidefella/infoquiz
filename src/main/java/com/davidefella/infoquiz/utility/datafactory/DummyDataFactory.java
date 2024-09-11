package com.davidefella.infoquiz.utility.datafactory;

import com.davidefella.infoquiz.model.persistence.*;
import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.service.*;
import com.davidefella.infoquiz.utility.DecimalRounder;
import com.davidefella.infoquiz.utility.StartupDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Classe che produce dati di test e li salva nel database.
 * Utile per test e demo
 * */
@Component
public class DummyDataFactory {

    private static final Logger logger = LoggerFactory.getLogger(StartupDataLoader.class);

    private final AnswerService answerService;
    private final EvaluationService evaluationService;
    private final EvaluationStudentService evaluationStudentService;
    private final UserInfoQuizService userInfoQuizService;
    private final QuestionService questionItemService;
    private final ClassroomService classroomService;

    @Autowired
    public DummyDataFactory(AnswerService answerService, EvaluationService evaluationService,
                            EvaluationStudentService evaluationStudentService, UserInfoQuizService userInfoQuizService,
                            QuestionService questionItemService, ClassroomService classroomService) {
        this.answerService = answerService;
        this.evaluationService = evaluationService;
        this.evaluationStudentService = evaluationStudentService;
        this.userInfoQuizService = userInfoQuizService;
        this.questionItemService = questionItemService;
        this.classroomService = classroomService;
    }

    public void loadAllDummyData() {
        loadUserAndClassroomsData();
        loadEvaluationData();
        loadEvaluationStudentData();
        loadQuestionData();
        loadAnswerData();

    }

    private void loadUserAndClassroomsData() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password";

        List<Classroom> classrooms = Arrays.asList(
                new Classroom("BLUE", "BLue room", null, null),
                new Classroom("YELLOW", "Yellow room", null, null),
                new Classroom("ORANGE", "Orange room", null, null)
        );

        classroomService.saveAll(classrooms);

        List<UserInfoQuiz> userInfoQuizs = new ArrayList<>(List.of(
                new Student("Cognome 1", "Nome 1", null, null, classrooms.get(1)),
                new Student("Cognome 2", "Nome 2", null, null, classrooms.get(1)),
                new Student("Cognome 3", "Nome 3", null, null, classrooms.get(2)),
                new Teacher("F", "D", "fd@gmail.com", encoder.encode(rawPassword), new ArrayList<>(Arrays.asList("Java", "Database"))),
                new Teacher("E", "S", "es@gmail.com", encoder.encode(rawPassword), List.of("JavaScript")),
                new Teacher("T", "T", "test@gmail.com", encoder.encode(rawPassword),  new ArrayList<>(Arrays.asList("JavaScript")))));


        userInfoQuizService.saveAll(userInfoQuizs);

        classrooms.get(1).getTeachers().add((Teacher) userInfoQuizs.get(3));
        classrooms.get(2).getTeachers().add((Teacher) userInfoQuizs.get(3));
        classrooms.get(2).getTeachers().add((Teacher) userInfoQuizs.get(4));

        // Salva gli insegnanti
        userInfoQuizService.saveAll(Arrays.asList(userInfoQuizs.get(3), userInfoQuizs.get(4)));

        // Salva nuovamente le classi per sincronizzare il lato classroom-teachers
        classroomService.saveAll(classrooms);
    }


    private void loadEvaluationData() {
        Teacher t1 = (Teacher) userInfoQuizService.findByEmail("fd@gmail.com").get();

        List<Evaluation> evaluations = Arrays.asList(
                new Evaluation("Evaluation 1", LocalDate.now(), "Descrizione Evaluation 1", t1, true),
                new Evaluation("Evaluation 2", LocalDate.now().minusMonths(1), "Descrizione Evaluation 2", t1, true),
                new Evaluation("Evaluation 3", LocalDate.now().minusMonths(2), "Descrizione Evaluation 3", t1, true)
        );

        evaluationService.saveAll(evaluations);

        logger.info("Loaded evaluations");
    }

    private void loadQuestionData() {
        for (Evaluation evaluation : evaluationService.findAll()) {
            List<Question> questions = Arrays.asList(
                    new Question(evaluation, "Question 1 ?"),
                    new Question(evaluation, "Question 2 ?"),
                    new Question(evaluation, "Question 3 ?"),
                    new Question(evaluation, "Question 4 ?"),
                    new Question(evaluation, "Question 5 ?")
            );

            for (Question question : questions) {
                questionItemService.save(question);
            }
        }

        logger.info("Loaded questions");

    }

    private void loadAnswerData() {
        List<Question> questions = questionItemService.findAll();

        for (Question question : questions) {
            List<Answer> answers = Arrays.asList(
                    new Answer(question, "Correct answer for " + question.getQuestionText(), true),
                    new Answer(question, "answer 2 <code> Some Code </code>?\n ", false),
                    new Answer(question, "answer 3 ", false),
                    new Answer(question, "answer 4 ", false)
            );

            answerService.saveAll(answers);

        }

        logger.info("Loaded answers");

    }

    private void loadEvaluationStudentData() {
        List<Student> students = userInfoQuizService.findAllStudents();

        List<Evaluation> evaluations = evaluationService.findAll();

        for (Student student : students) {
            for (Evaluation evaluation : evaluations) {
                EvaluationStudent evaluationStudent = new EvaluationStudent();
                evaluationStudent.setStudent(student);
                evaluationStudent.setEvaluation(evaluation);

                // Random score between 0 and 100
                evaluationStudent.setScore(DecimalRounder.roundToTwoDecimals(Math.random() * 10));
                evaluationStudentService.save(evaluationStudent);
            }
        }

        logger.info("Loaded students sessions");

    }
}