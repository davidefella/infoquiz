package com.davidefella.infoquiz.util;

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
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

@ActiveProfiles("test")
@Component
public class DummyTestDataFactory {

    private static final Logger logger = LoggerFactory.getLogger(StartupDataLoader.class);

    private final AnswerService answerService;
    private final EvaluationService evaluationService;
    private final EvaluationStudentService evaluationStudentService;
    private final UserInfoQuizService userInfoQuizService;

    private final QuestionService questionItemService;
    private final ClassroomService classroomService;

    @Autowired
    public DummyTestDataFactory(AnswerService answerService, EvaluationService evaluationService,
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
        loadQuestionData();
        loadAnswerData();
        loadEvaluationStudentData();
    }

    public void loadUserAndClassroomsData() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("password");

        List<Classroom> classrooms = Arrays.asList(
                new Classroom(UUIDRegistry.CLASSROOM_1_BLUE, "BLUE", "Empty Classroom", null, null),
                new Classroom(UUIDRegistry.CLASSROOM_2_YELLOW, "YELLOW", "2 students", null, null),
                new Classroom(UUIDRegistry.CLASSROOM_3_ORANGE, "ORANGE", "1 students", null, null)
        );
        classroomService.saveAll(classrooms);

        List<UserInfoQuiz> userInfoQuizs = new ArrayList<>(List.of(
                new Student(UUIDRegistry.STUDENT_1, "T_Cognome Studente 1", "T_Nome Studente 1", null, null, classroomService.findByUUID(UUIDRegistry.CLASSROOM_2_YELLOW)),
                new Student(UUIDRegistry.STUDENT_2, "T_Cognome Studente 2", "T_Nome Studente 2", null, null, classroomService.findByUUID(UUIDRegistry.CLASSROOM_2_YELLOW)),
                new Student(UUIDRegistry.STUDENT_3, "T_Cognome Studente 3", "T_Nome Studente 3", null, null, classroomService.findByUUID(UUIDRegistry.CLASSROOM_3_ORANGE)),
                new Teacher(UUIDRegistry.TEACHER_1, "F", "D", "T_fd@gmail.com", encodedPassword),
                new Teacher(UUIDRegistry.TEACHER_2, "E", "S", "T_es@gmail.com", encodedPassword),
                new Teacher(UUIDRegistry.TEACHER_3, "T", "T", "T_te@gmail.com", encodedPassword)));

        userInfoQuizService.saveAll(userInfoQuizs);

        // Associa i Teacher alle Classroom e viceversa
        Classroom yellowClassroom = classroomService.findByUUID(UUIDRegistry.CLASSROOM_2_YELLOW);
        Classroom orangeClassroom = classroomService.findByUUID(UUIDRegistry.CLASSROOM_3_ORANGE);

        Teacher teacher1 = userInfoQuizService.findTeacherByUUID(UUIDRegistry.TEACHER_1).get();
        Teacher teacher2 = userInfoQuizService.findTeacherByUUID(UUIDRegistry.TEACHER_2).get();
        Teacher teacher3 = userInfoQuizService.findTeacherByUUID(UUIDRegistry.TEACHER_3).get();

        yellowClassroom.getTeachers().add(teacher1);
        orangeClassroom.getTeachers().add(teacher2);
        orangeClassroom.getTeachers().add(teacher3);

        classroomService.saveAll(Arrays.asList(yellowClassroom, orangeClassroom));
    }

    private void loadEvaluationData() {
        Teacher t1 = (Teacher) userInfoQuizService.findByUUID(UUIDRegistry.TEACHER_3).get();
        Teacher t2 = (Teacher) userInfoQuizService.findByUUID(UUIDRegistry.TEACHER_2).get();

        List<Evaluation> evaluations = List.of(
                new Evaluation(UUIDRegistry.EVALUATION_1, "T Evaluation 1", LocalDate.now(), "T1 Evaluation Test", t1, true),
                new Evaluation(UUIDRegistry.EVALUATION_2, "T Evaluation 2", LocalDate.now(), "T2 Evaluation Test", t1, true),
                new Evaluation(UUIDRegistry.EVALUATION_3, "T Evaluation 3", LocalDate.now(), "T3 Evaluation Test", t2, true)

        );

        evaluationService.saveAll(evaluations);
        logger.info("Loaded Evaluations");
    }

    private void loadQuestionData() {
        Evaluation e1 = evaluationService.findByUUID(UUIDRegistry.EVALUATION_1);
        Evaluation e3 = evaluationService.findByUUID(UUIDRegistry.EVALUATION_3);

        List<Question> questions = Arrays.asList(
                new Question(UUIDRegistry.QUESTION_1, e1, "Question 1 ?"),
                new Question(UUIDRegistry.QUESTION_2, e1, "Question 2 ?"),
                new Question(UUIDRegistry.QUESTION_3, e1, "Question 3 ?"),
                new Question(UUIDRegistry.QUESTION_4, e3, "Question 1 ?"),
                new Question(UUIDRegistry.QUESTION_5, e3, "Question 2 ?"),
                new Question(UUIDRegistry.QUESTION_6, e3, "Question 3 ?")
        );

        questionItemService.saveAll(questions);
        logger.info("Loaded Questions");
    }

    private void loadAnswerData() {
        Question q1 = questionItemService.findByUUID(UUIDRegistry.QUESTION_1).get();
        Question q2 = questionItemService.findByUUID(UUIDRegistry.QUESTION_2).get();
        Question q3 = questionItemService.findByUUID(UUIDRegistry.QUESTION_3).get();


        List<Answer> answersQ1 = Arrays.asList(
                new Answer(UUIDRegistry.ANSWER_1_Q1, q1, "Correct answer", true),
                new Answer(UUIDRegistry.ANSWER_2_Q1, q1, "Wrong answer", false),
                new Answer(UUIDRegistry.ANSWER_3_Q1, q1, "Wrong answer", false)
        );
        answerService.saveAll(answersQ1);

        List<Answer> answersQ2 = Arrays.asList(
                new Answer(UUIDRegistry.ANSWER_1_Q2, q2, "Wrong answer", false),
                new Answer(UUIDRegistry.ANSWER_2_Q2, q2, "Correct answer", true),
                new Answer(UUIDRegistry.ANSWER_3_Q2, q2, "Wrong answer", false)
        );
        answerService.saveAll(answersQ2);

        List<Answer> answersQ3 = Arrays.asList(
                new Answer(UUIDRegistry.ANSWER_1_Q3, q3, "Wrong answer", false),
                new Answer(UUIDRegistry.ANSWER_2_Q3, q3, "Wrong answer", false),
                new Answer(UUIDRegistry.ANSWER_3_Q3, q3, "Correct answer", true)
        );
        answerService.saveAll(answersQ3);

        logger.info("Loaded answers");
    }

    private void loadEvaluationStudentData() {
        Student s2 = userInfoQuizService.findStudentByUUID(UUIDRegistry.STUDENT_2).get();
        Student s3 = userInfoQuizService.findStudentByUUID(UUIDRegistry.STUDENT_3).get();
        Evaluation e1 = evaluationService.findByUUID(UUIDRegistry.EVALUATION_1);

        List<EvaluationStudent> evaluationStudents = Arrays.asList(
                new EvaluationStudent(UUIDRegistry.EVALUATION_STUDENT_1, e1, s3, 5.9),
                new EvaluationStudent(UUIDRegistry.EVALUATION_STUDENT_2, e1, s2, 10.0),
                new EvaluationStudent(UUIDRegistry.EVALUATION_STUDENT_3, e1, s2, 11));

        evaluationStudentService.saveAll(evaluationStudents);
    }
}