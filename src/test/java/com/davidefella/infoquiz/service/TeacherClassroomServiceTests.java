package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
import com.davidefella.infoquiz.util.DummyTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TeacherClassroomServiceTests {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserInfoQuizService userInfoQuizService;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }

    @Test
    void testFindByTeacherClassrooms() {
        // Trova il docente tramite email
        Teacher teacherT1 = (Teacher) userInfoQuizService.findByEmail("T_fd@gmail.com").get();

        // Recupera le classi assegnate al docente
        List<Classroom> classrooms = classroomService.findClassroomsByTeacherEmail(teacherT1.getEmail());

        // Verifica che non sia null e che la lista non sia vuota
        assertNotNull(classrooms);
        assertFalse(classrooms.isEmpty());

        // Verifica il numero e i dettagli delle classi assegnate
        assertEquals(2, classrooms.size());  // T_fd ha 2 classi

        assertEquals("YELLOW", classrooms.get(0).getCode());
        assertEquals(2, classrooms.get(0).getStudents().size());

        assertEquals("ORANGE", classrooms.get(1).getCode());
        assertEquals(1, classrooms.get(1).getStudents().size());
    }

    @Test
    void testFindByTeacherNoClassrooms() {
        // Trova il docente tramite email
        Teacher teacherT2 = (Teacher) userInfoQuizService.findByEmail("T_es@gmail.com").get();

        // Recupera le classi assegnate al docente (T_es ha 1 classe)
        List<Classroom> classrooms = classroomService.findClassroomsByTeacherEmail(teacherT2.getEmail());

        // Verifica che la lista non sia null ma sia vuota
        assertNotNull(classrooms);
        assertEquals(1, classrooms.size());
        assertEquals("ORANGE", classrooms.get(0).getCode());
    }
}