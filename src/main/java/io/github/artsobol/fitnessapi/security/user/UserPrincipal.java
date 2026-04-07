package io.github.artsobol.fitnessapi.security.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserPrincipal(
        Long userId, String username, Collection<? extends GrantedAuthority> authorities
) {
}
