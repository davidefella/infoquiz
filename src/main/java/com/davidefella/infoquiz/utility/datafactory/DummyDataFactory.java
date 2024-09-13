package com.davidefella.infoquiz.utility.datafactory;

import com.davidefella.infoquiz.model.persistence.*;
import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.model.persistence.users.UserInfoQuiz;
import com.davidefella.infoquiz.service.*;
import com.davidefella.infoquiz.utility.StartupDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final EvaluationSessionService evaluationSessionService;

    /*Data*/
    List<Classroom> classrooms;
    List<UserInfoQuiz> userInfoQuizs;
    List<Evaluation> evaluations;
    List<Question> questions;
    List<EvaluationSession> sessions;

    @Autowired
    public DummyDataFactory(AnswerService answerService, EvaluationService evaluationService,
                            EvaluationStudentService evaluationStudentService, UserInfoQuizService userInfoQuizService,
                            QuestionService questionItemService, ClassroomService classroomService, EvaluationSessionService evaluationSessionService) {
        this.answerService = answerService;
        this.evaluationService = evaluationService;
        this.evaluationStudentService = evaluationStudentService;
        this.userInfoQuizService = userInfoQuizService;
        this.questionItemService = questionItemService;
        this.classroomService = classroomService;
        this.evaluationSessionService = evaluationSessionService;
    }

    public void loadAllDummyData() {
        loadUserAndClassroomsData();
        loadEvaluationData();
        loadEvaluationAndSessionsStudentData();
        loadQuestionData();
        loadAnswerData();

    }
    public void loadUserAndClassroomsData() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("password");

        classrooms = Arrays.asList(
                new Classroom("BLUE", "Empty Classroom", null, null),
                new Classroom("YELLOW", "2 students", null, null),
                new Classroom("ORANGE", "1 students", null, null));
        classroomService.saveAll(classrooms);

        userInfoQuizs = new ArrayList<>(List.of(
                new Student("T_Cognome Studente 1", "T_Nome Studente 1", null, null, classrooms.get(1)),
                new Student("T_Cognome Studente 2", "T_Nome Studente 2", null, null, classrooms.get(1)),
                new Student("T_Cognome Studente 3", "T_Nome Studente 3", null, null, classrooms.get(2)),
                new Teacher("F", "D", "T_fd@gmail.com", encodedPassword),
                new Teacher("E", "S", "T_es@gmail.com", encodedPassword),
                new Teacher("T", "T", "T_te@gmail.com", encodedPassword)));
        userInfoQuizs = userInfoQuizService.saveAll(userInfoQuizs);

        Classroom yellowClassroom = classrooms.get(1);
        Classroom orangeClassroom = classrooms.get(2);

        Teacher teacher1 = (Teacher) userInfoQuizs.get(3);
        Teacher teacher2 = (Teacher) userInfoQuizs.get(4);
        Teacher teacher3 = (Teacher) userInfoQuizs.get(5);

        yellowClassroom.getTeachers().add(teacher1);
        orangeClassroom.getTeachers().add(teacher2);
        orangeClassroom.getTeachers().add(teacher3);

        classroomService.saveAll(Arrays.asList(yellowClassroom, orangeClassroom));
        classroomService.saveAll(classrooms);
    }

    private void loadEvaluationData() {
        evaluations = List.of(
                new Evaluation("T Evaluation 1", LocalDate.now(), "T1 Evaluation Test", (Teacher) userInfoQuizs.get(3), true),
                new Evaluation("T Evaluation 2", LocalDate.now(), "T2 Evaluation Test", (Teacher) userInfoQuizs.get(3), true),
                new Evaluation("T Evaluation 3", LocalDate.now(), "T3 Evaluation Test", (Teacher) userInfoQuizs.get(4), true)

        );
        evaluations = evaluationService.saveAll(evaluations);
    }

    private void loadQuestionData() {
        questions = Arrays.asList(
                new Question(evaluations.get(0), "Question 1 ?"),
                new Question(evaluations.get(0), "Question 2 ?"),
                new Question(evaluations.get(0), "Question 3 ?"),
                new Question(evaluations.get(2), "Question 1 ?"),
                new Question(evaluations.get(2), "Question 2 ?"),
                new Question(evaluations.get(2), "Question 3 ?")
        );

        questions = questionItemService.saveAll(questions);
    }

    private void loadAnswerData() {

        List<Answer> answersQ1 = Arrays.asList(
                new Answer(questions.get(0), "Correct answer", true),
                new Answer(questions.get(0), "Wrong answer", false),
                new Answer(questions.get(0), "Wrong answer", false)
        );
        answerService.saveAll(answersQ1);

        List<Answer> answersQ2 = Arrays.asList(
                new Answer(questions.get(1), "Wrong answer", false),
                new Answer(questions.get(1), "Correct answer", true),
                new Answer(questions.get(1), "Wrong answer", false)
        );
        answerService.saveAll(answersQ2);

        List<Answer> answersQ3 = Arrays.asList(
                new Answer(questions.get(2), "Wrong answer", false),
                new Answer(questions.get(2), "Wrong answer", false),
                new Answer(questions.get(2), "Correct answer", true)
        );
        answerService.saveAll(answersQ3);

        logger.info("Loaded answers");
    }

    private void loadEvaluationAndSessionsStudentData() {
        sessions = Arrays.asList(
                new EvaluationSession(LocalDate.of(2024,9,30), LocalTime.of(10,0), LocalTime.of(12,0), EvaluationSessionStatus.PLANNED, evaluations.get(0), classrooms.get(1) ),
                new EvaluationSession(LocalDate.of(2024,9,30), LocalTime.of(14,0), LocalTime.of(16,0), EvaluationSessionStatus.PLANNED, evaluations.get(0), classrooms.get(1) ),
                new EvaluationSession(LocalDate.of(2024,6,30), LocalTime.of(9,0), LocalTime.of(12,0), EvaluationSessionStatus.COMPLETED, evaluations.get(0), classrooms.get(1) ),
                new EvaluationSession(LocalDate.of(2024,10,30), LocalTime.of(9,0), LocalTime.of(13,0), EvaluationSessionStatus.STANDBY, evaluations.get(2), null )
                );

        evaluationSessionService.saveAll(sessions);

        List<EvaluationStudent> evaluationStudents = Arrays.asList(
                new EvaluationStudent(sessions.getFirst(), (Student) userInfoQuizs.get(2), 5.9),
                new EvaluationStudent(sessions.getFirst(), (Student) userInfoQuizs.get(1), 10.0),
                new EvaluationStudent(sessions.getFirst(), (Student) userInfoQuizs.get(1), 11));

        evaluationStudentService.saveAll(evaluationStudents);
    }
}