package com.davidefella.infoquiz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;

public class TokenExtractor {

    public static String extractTokenFromAuth(MvcResult authResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonResponse = authResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonResponse).get("token").asText();
    }
}
