package com.davidefella.infoquiz.service;

import com.davidefella.infoquiz.model.persistence.EvaluationSession;
import com.davidefella.infoquiz.repository.EvaluationSessionRepository;
import com.davidefella.infoquiz.util.DummyTestDataFactory;
import com.davidefella.infoquiz.util.UUIDRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class EvaluationSessionServiceTest {

    @Autowired
    private EvaluationSessionRepository evaluationSessionRepository;

    @Autowired
    private EvaluationSessionService evaluationSessionService;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    private EvaluationSession evaluationSession;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }
    @Test
    public void testFindByUUID_Success() {
        EvaluationSession result = evaluationSessionService.findByUUID(UUIDRegistry.EVALUATION_SESSION_1);

        assertNotNull(result);
        assertEquals(UUIDRegistry.EVALUATION_SESSION_1, result.getUuid());
    }

    @Test
    public void testFindByUUID_NotFound() {
        UUID nonExistentUUID = UUID.randomUUID();

        assertThrows(RuntimeException.class, () -> evaluationSessionService.findByUUID(nonExistentUUID));
    }
}
