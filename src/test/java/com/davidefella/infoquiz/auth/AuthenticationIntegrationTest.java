package com.davidefella.infoquiz.auth;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Disabled
    public void testLoginSuccess() {
        // Prepara i dati di login
        String loginUrl = "/api/auth/authenticate";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestBody = "{\"email\": \"fd@gmail.com\", \"password\": \"admin\"}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Esegui la richiesta POST per simulare il login
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, request, String.class);

        // Verifica che il login sia andato a buon fine
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLoginFailure() {
        // Prepara i dati di login non validi
        String loginUrl = "/api/login";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestBody = "{\"email\": \"wrongEmail.com\", \"password\": \"admin\"}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Esegui la richiesta POST per simulare il login fallito
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, request, String.class);

        // Verifica che il login fallisca
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
