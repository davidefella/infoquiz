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
class ClassroomControllerTest {

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
    void testClassroomsForTeacherWhenAuthenticatedThenReturnClassrooms() throws Exception {
        MvcResult authResult = mvc.perform(post(ApiEndpoints.AUTH_TOKEN_V1)
                                  .with(httpBasic("T_fd@gmail.com", "password")))
                                  .andExpect(status().isOk())
                                  .andReturn();

        String token = TokenExtractor.extractTokenFromAuth(authResult);

        assertNotNull(token);

        mvc.perform(get(ApiEndpoints.TEACHER_CLASSROOMS_V1)
                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.classrooms[0].code").value("YELLOW"))
                        .andExpect(jsonPath("$.classrooms[0].countStudents").value("2"))
                        .andExpect(jsonPath("$.classrooms[1].code").value("ORANGE"))
                        .andExpect(jsonPath("$.classrooms[1].countStudents").value("1"))
                        .andReturn();
    }

    @Test
    void testClassroomsForTeacherWhenUnauthenticatedThenReturn401() throws Exception {
        mvc.perform(get(ApiEndpoints.TEACHER_CLASSROOMS_V1))
                .andExpect(status().isUnauthorized());
    }
}