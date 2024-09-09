package com.davidefella.infoquiz.controller;

import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import com.davidefella.infoquiz.util.DummyTestDataFactory;
import com.davidefella.infoquiz.util.TokenExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EvaluationControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }

    @Test
    void testEvaluationsForTeacherWhenAuthenticatedThenReturnEvaluations() throws Exception {
        MvcResult authResult = mvc.perform(post(ApiEndpoints.AUTH_TOKEN_V1)
                        .with(httpBasic("T_fd@gmail.com", "password")))
                .andExpect(status().isOk())
                .andReturn();

        String token = TokenExtractor.extractTokenFromAuth(authResult);

        assertNotNull(token);

        mvc.perform(get(ApiEndpoints.TEACHER_EVALUATIONS_V1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evaluations[0].title").value("T Evaluation 1")) // Verifica il primo elemento nell'array 'evaluations'
                .andExpect(jsonPath("$.evaluations[1].title").value("T Evaluation 2")) // Verifica il secondo elemento
                .andReturn();
    }


    @Test
    void testEvaluationsForTeacherWhenUnauthenticatedThenReturn401() throws Exception {
        mvc.perform(get(ApiEndpoints.TEACHER_EVALUATIONS_V1))
                .andExpect(status().isUnauthorized());

    }
}
