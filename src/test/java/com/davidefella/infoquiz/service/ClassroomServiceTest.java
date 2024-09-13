package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import com.davidefella.infoquiz.model.persistence.Classroom;
import com.davidefella.infoquiz.model.persistence.users.Student;
import com.davidefella.infoquiz.util.DummyTestDataFactory;
import com.davidefella.infoquiz.util.UUIDRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ClassroomServiceTest {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }

    @Test
    public void testFindClassroomByUUID_ClassroomExists_ReturnsClassroom() {
        Classroom classroomYellow = classroomService.findByUUID(UUIDRegistry.CLASSROOM_2_YELLOW);

        assertNotNull(classroomYellow);
        assertEquals(UUIDRegistry.CLASSROOM_2_YELLOW, classroomYellow.getUuid());
    }

    @Test
    public void testFindClassroomByUUID_ClassroomNotExists_ThrowsResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            classroomService.findByUUID(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        });
    }

    @Test
    public void testFindClassroomByTeacherEmail_ClassroomExists_ReturnsClassroom() {

        List<Classroom> classrooms = classroomService.findClassroomsByTeacherEmail("T_fd@gmail.com");

        assertNotNull(classrooms);
        assertEquals(1, classrooms.size());
        assertEquals(UUIDRegistry.CLASSROOM_2_YELLOW, classrooms.getFirst().getUuid());
    }

    @Test
    public void testFindClassroomByTeacherEmail_EmailNotExists_ReturnsEmptyClassroom() {

        List<Classroom> classrooms = classroomService.findClassroomsByTeacherEmail("notExists@gmail.com");

        assertNotNull(classrooms);
        assertEquals(0, classrooms.size());

    }

    @Test
    public void testBidirectionalRelationship_ClassroomHasStudents() {
        Classroom classroomYellow = classroomService.findByUUID(UUIDRegistry.CLASSROOM_2_YELLOW);

        List<Student> studentsInYellow = classroomYellow.getStudents();
        assertNotNull(studentsInYellow);
        assertEquals(2, studentsInYellow.size());

        for (Student student : studentsInYellow) {
            assertEquals(classroomYellow, student.getClassroom());
        }
    }
}