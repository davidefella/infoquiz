package com.davidefella.infoquiz.auth;

import com.davidefella.infoquiz.authentication.configuration.RsaKeyProperties;
import com.davidefella.infoquiz.authentication.configuration.SecurityConfiguration;
import com.davidefella.infoquiz.authentication.service.JwtService;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import com.davidefella.infoquiz.util.DummyTestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({RsaKeyProperties.class, SecurityConfiguration.class, JwtService.class, DummyTestDataFactory.class})
@ActiveProfiles("test")
class AuthenticationServiceTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DummyTestDataFactory dummyTestDataFactory;

    @BeforeEach
    @Transactional
    void setUp() {
        dummyTestDataFactory.loadAllDummyData();
    }

    @Test
    void testWhenUnauthenticatedThen401_1() throws Exception {
        mvc.perform(get("/")).andExpect(status().isUnauthorized());

        mvc.perform(post(ApiEndpoints.AUTH_TOKEN)
                        .with(httpBasic("wrong@gmail.com", "password")))
                .andExpect(status().isUnauthorized())
                .andReturn();

        mvc.perform(post(ApiEndpoints.AUTH_TOKEN)
                        .with(httpBasic("T_fd@gmail.com", "wrong")))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void testWhenAuthenticatedThenCheckToken() throws Exception {
        MvcResult result = mvc.perform(post(ApiEndpoints.AUTH_TOKEN)
                              .with(httpBasic("T_fd@gmail.com", "password")))
                              .andExpect(status().isOk())
                              .andReturn();

        String token = result.getResponse().getContentAsString();
        assertNotNull(token);
    }
}