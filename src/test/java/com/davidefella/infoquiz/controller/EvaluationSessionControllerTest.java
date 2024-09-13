package com.davidefella.infoquiz.controller;


import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import com.davidefella.infoquiz.repository.EvaluationSessionRepository;
import com.davidefella.infoquiz.util.DummyTestDataFactory;
import com.davidefella.infoquiz.util.TokenExtractor;
import com.davidefella.infoquiz.util.UUIDRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class EvaluationSessionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EvaluationSessionRepository evaluationSessionRepository;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }


    /* uuid is for evaluation, not for specific evaluationSession*/
    @Test
    void testGetEvaluationSessionsForEvaluation_Success() throws Exception {
        MvcResult authResult = mvc.perform(post(ApiEndpoints.AUTH_TOKEN)
                        .with(httpBasic("T_fd@gmail.com", "password")))
                .andExpect(status().isOk())
                .andReturn();

        String token = TokenExtractor.extractTokenFromAuth(authResult);
        assertNotNull(token);

        MvcResult result = mvc.perform(get(ApiEndpoints.TEACHER_SESSIONS_V1+"/"+UUIDRegistry.EVALUATION_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evaluationSessions", hasSize(3)))
                .andReturn();

        System.out.println(result);
    }

    @Test
    public void testGetEvaluationSessionsForEvaluation_NotFound() throws Exception {
        MvcResult authResult = mvc.perform(post(ApiEndpoints.AUTH_TOKEN)
                        .with(httpBasic("T_fd@gmail.com", "password")))
                .andExpect(status().isOk())
                .andReturn();

        String token = TokenExtractor.extractTokenFromAuth(authResult);
        assertNotNull(token);


        mvc.perform(get(ApiEndpoints.TEACHER_EVALUATIONS_V1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evaluations", hasSize(0)))  // Nessuna valutazione per questo insegnante
                .andReturn();


        mvc.perform(get(ApiEndpoints.TEACHER_SESSIONS_V1+"/22222222-2222-2222-2222-222222222222")
                        .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evaluationSessions", hasSize(0)))  // Nessuna valutazione per questo insegnante
                .andReturn();
    }
}