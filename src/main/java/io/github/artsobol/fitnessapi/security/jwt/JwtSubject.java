package io.github.artsobol.fitnessapi.security.jwt;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record JwtSubject(
        Long userId,
        Collection<? extends GrantedAuthority> authorities,
        String username
) {
}