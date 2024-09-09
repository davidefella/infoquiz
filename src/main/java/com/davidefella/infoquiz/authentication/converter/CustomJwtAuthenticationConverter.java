package com.davidefella.infoquiz.authentication.converter;

import com.davidefella.infoquiz.authentication.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public CustomJwtAuthenticationConverter(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        // Estrarre l'email dal claim 'sub'
        String email = jwt.getClaimAsString("sub");

        if (email == null) {
            throw new UsernameNotFoundException("Email not found in token");
        }

        // Estrarre i ruoli (authorities) dal token JWT
        List<String> roles = jwt.getClaimAsStringList("scope"); // Supponendo che i ruoli siano nel claim 'roles'

        if (roles == null) {
            throw new IllegalArgumentException("Roles not found in token");
        }

        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        return new JwtAuthenticationToken(jwt, authorities);

    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles != null) {
            return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
        }
        return List.of();
    }
}
