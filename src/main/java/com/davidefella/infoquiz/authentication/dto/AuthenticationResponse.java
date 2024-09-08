package com.davidefella.infoquiz.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private long expirationTime;  // Aggiunto
}
