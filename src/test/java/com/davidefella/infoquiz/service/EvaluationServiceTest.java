package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.users.Teacher;
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
public class EvaluationServiceTest {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }

    @Test
    public void testFindEvaluationByUUID_EvaluationExists_ReturnsEvaluation() {
        Evaluation evaluation1 = evaluationService.findByUUID(UUIDRegistry.EVALUATION_1);

        assertNotNull(evaluation1);
        assertEquals(UUIDRegistry.EVALUATION_1, evaluation1.getUuid());
        assertEquals("T Evaluation 1", evaluation1.getTitle());
    }

    @Test
    public void testFindEvaluationByUUID_EvaluationNotExists_ThrowsResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            evaluationService.findByUUID(UUID.fromString("b1111111-e89b-12d3-a456-42661417400d"));
        });
    }

    @Test
    public void testFindEvaluationsByTeacher_TeacherExists_ReturnsEvaluations() {
        List<Evaluation> evaluationsForTeacher3 = evaluationService.findByEmailAssignedTeacher("T_te@gmail.com");

        assertNotNull(evaluationsForTeacher3);
        assertEquals(2, evaluationsForTeacher3.size());
        assertEquals(UUIDRegistry.EVALUATION_1, evaluationsForTeacher3.get(0).getUuid());
        assertEquals(UUIDRegistry.EVALUATION_2, evaluationsForTeacher3.get(1).getUuid());
    }

    @Test
    public void testFindEvaluationsByTeacher_TeacherNotExists_ReturnsEmptyList() {
        List<Evaluation> evaluations = evaluationService.findByEmailAssignedTeacher("notexists@gmail.com");

        assertNotNull(evaluations);
        assertTrue(evaluations.isEmpty());
    }

    @Test
    public void testBidirectionalRelationship_EvaluationHasTeacher() {
        Evaluation evaluation1 = evaluationService.findByUUID(UUIDRegistry.EVALUATION_1);

        Teacher assignedTeacher = evaluation1.getAssignedTeacher();
        assertNotNull(assignedTeacher);
        assertEquals(UUIDRegistry.TEACHER_3, assignedTeacher.getUuid());
    }
}
