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
import com.davidefella.infoquiz.util.UUIDRegistry;
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
        Teacher teacherT1 = (Teacher) userInfoQuizService.findByEmail("T_fd@gmail.com").get();

        List<Classroom> classrooms = classroomService.findClassroomsByTeacherEmail(teacherT1.getEmail());

        assertNotNull(classrooms);
        assertFalse(classrooms.isEmpty());

        assertEquals(2, classrooms.size());  // T_fd ha 2 classi

        assertEquals("YELLOW", classrooms.get(0).getCode());
        assertEquals(2, classrooms.get(0).getStudents().size());

        assertEquals("ORANGE", classrooms.get(1).getCode());
        assertEquals(1, classrooms.get(1).getStudents().size());
    }

    @Test
    void testFindByTeacherNoClassrooms() {
        Teacher teacherT2 = (Teacher) userInfoQuizService.findByEmail("T_es@gmail.com").get();

        List<Classroom> classrooms = classroomService.findClassroomsByTeacherEmail(teacherT2.getEmail());

        assertNotNull(classrooms);
        assertEquals(1, classrooms.size());
        assertEquals("ORANGE", classrooms.get(0).getCode());
    }

    @Test
    void testFindStudentsByClassroomUuid() {
        Classroom classroom = classroomService.findByUUID(UUIDRegistry.CLASSROOM_2_YELLOW).orElseThrow();

        assertNotNull(classroom);
        assertEquals("YELLOW", classroom.getCode());

        assertNotNull(classroom.getStudents());
        assertEquals(2, classroom.getStudents().size());

        assertEquals("T_Cognome Studente 1", classroom.getStudents().get(0).getLastName());
        assertEquals("T_Cognome Studente 2", classroom.getStudents().get(1).getLastName());
    }

    @Test
    void testFindEmptyClassroomStudents() {
        Classroom classroom = classroomService.findByUUID(UUIDRegistry.CLASSROOM_1_BLUE).orElseThrow();

        assertNotNull(classroom);
        assertEquals("BLUE", classroom.getCode());
        assertTrue(classroom.getStudents().isEmpty());
    }
}