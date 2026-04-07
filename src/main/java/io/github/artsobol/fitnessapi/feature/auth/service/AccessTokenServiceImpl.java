package io.github.artsobol.fitnessapi.feature.auth.service;

import io.github.artsobol.fitnessapi.feature.user.entity.User;
import io.github.artsobol.fitnessapi.security.jwt.JwtSubject;
import io.github.artsobol.fitnessapi.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String createAccessToken(User user) {
        log.info("Creating access token for user: {}", user.getUsername());
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.getRole().name()));
        JwtSubject subject = new JwtSubject(user.getId(), authorities, user.getUsername());
        String token = jwtTokenProvider.generateToken(subject);

        log.info("Access token created for user: {}", user.getUsername());
        return token;
    }
}
