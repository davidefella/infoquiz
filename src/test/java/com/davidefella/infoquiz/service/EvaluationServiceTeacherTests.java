package com.davidefella.infoquiz.service;
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
class EvaluationServiceTeacherTests {

    @Autowired
    private EvaluationService evaluationService;

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
    void testFindByAssignedTeacher() {
        Teacher teacherT1 = (Teacher) userInfoQuizService.findByEmail("T_fd@gmail.com").get();

        List<Evaluation> evaluations = evaluationService.findByAssignedTeacher(teacherT1);

        assertNotNull(evaluations);
        assertFalse(evaluations.isEmpty());

        assertEquals(2, evaluations.size());
        assertEquals("T Evaluation 1", evaluations.get(0).getTitle());
        assertEquals("T Evaluation 2", evaluations.get(1).getTitle());
    }

    @Test
    void testFindByAssignedTeacherEmpty() {
        Teacher teacherT2 = (Teacher) userInfoQuizService.findByEmail("T_es@gmail.com").get();

        List<Evaluation> evaluations = evaluationService.findByAssignedTeacher(teacherT2);

        assertNotNull(evaluations);
        assertTrue(evaluations.isEmpty());
    }
}
