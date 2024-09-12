package com.davidefella.infoquiz.authentication.controller;

import com.davidefella.infoquiz.authentication.dto.model.AuthenticationResponse;
import com.davidefella.infoquiz.authentication.service.JwtService;
import com.davidefella.infoquiz.controller.api.util.endpoints.ApiEndpoints;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final JwtService tokenService;

    public AuthController(JwtService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(ApiEndpoints.AUTH_TOKEN)
    public AuthenticationResponse token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        long expirationTime = tokenService.getExpirationTime();

        return new AuthenticationResponse(token, expirationTime);
    }
}
