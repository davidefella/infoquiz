package com.davidefella.infoquiz.authentication.controller;

import com.davidefella.infoquiz.authentication.dto.model.AuthenticationResponse;
import com.davidefella.infoquiz.authentication.service.JwtService;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final JwtService tokenService;

    public AuthController(JwtService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(ApiEndpoints.AUTH_TOKEN_V1)
    public AuthenticationResponse token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        long expirationTime = tokenService.getExpirationTime();

        // Ritorna un oggetto AuthenticationResponse con token e tempo di scadenza
        return new AuthenticationResponse(token, expirationTime);

    }
}
