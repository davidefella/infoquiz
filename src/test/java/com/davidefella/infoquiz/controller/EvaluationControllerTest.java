package com.davidefella.infoquiz.controller;

import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import com.davidefella.infoquiz.util.DummyTestDataFactory;
import com.davidefella.infoquiz.util.TokenExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class EvaluationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }

    @Test
    void whenUnauthenticated_thenReturn401ForTeacherEvaluations() throws Exception {
        mvc.perform(get(ApiEndpoints.TEACHER_EVALUATIONS_V1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenAuthenticated_thenReturnTeacherEvaluations() throws Exception {
        // Autenticazione e ottenimento token
        MvcResult authResult = mvc.perform(post(ApiEndpoints.AUTH_TOKEN)
                        .with(httpBasic("T_te@gmail.com", "password")))
                .andExpect(status().isOk())
                .andReturn();

        String token = TokenExtractor.extractTokenFromAuth(authResult);
        assertNotNull(token);

        // Verifica delle valutazioni restituite
        mvc.perform(get(ApiEndpoints.TEACHER_EVALUATIONS_V1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evaluations", hasSize(2)))  // Verifica che ci siano 2 valutazioni
                .andExpect(jsonPath("$.evaluations[0].title").value("T Evaluation 1")) // Verifica titolo prima valutazione
                .andExpect(jsonPath("$.evaluations[1].title").value("T Evaluation 2")) // Verifica titolo seconda valutazione
                .andReturn();
    }

    @Test
    void whenAuthenticated_thenReturnNoEvaluationsForNonExistentTeacher() throws Exception {
        // Autenticazione e ottenimento token per un insegnante senza valutazioni
        MvcResult authResult = mvc.perform(post(ApiEndpoints.AUTH_TOKEN)
                        .with(httpBasic("T_fd@gmail.com", "password")))
                .andExpect(status().isOk())
                .andReturn();

        String token = TokenExtractor.extractTokenFromAuth(authResult);
        assertNotNull(token);

        // Verifica che non ci siano valutazioni per questo insegnante
        mvc.perform(get(ApiEndpoints.TEACHER_EVALUATIONS_V1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evaluations", hasSize(0)))  // Nessuna valutazione per questo insegnante
                .andReturn();
    }

    @Test
    @Transactional
    void whenBadCredentials_thenReturnUnauthorized() throws Exception {
        mvc.perform(post(ApiEndpoints.AUTH_TOKEN)
                        .with(httpBasic("T_wrongemail@gmail.com", "password")))
                .andExpect(status().isUnauthorized());
    }
}
