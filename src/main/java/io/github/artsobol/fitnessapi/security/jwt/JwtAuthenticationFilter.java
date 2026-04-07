package io.github.artsobol.fitnessapi.security.jwt;

import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (!checkHeader(header)) {
            log.debug("Authorization header is missing or invalid");
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Claims claims;
            try {
                claims = parseToken(header);
            } catch (JwtException | IllegalArgumentException e) {
                SecurityContextHolder.clearContext();
                log.debug("Invalid token");
                filterChain.doFilter(request, response);
                return;
            }
            UserPrincipal userPrincipal = createUserPrincipal(claims);
            createAuthentication(userPrincipal, getAuthorities(claims));
            MDC.put("userId", userPrincipal.userId().toString());

            log.debug("User: {} authenticated with roles: {}", userPrincipal.username(), userPrincipal.authorities());
        }
    }

    private void createAuthentication(UserPrincipal userPrincipal, List<SimpleGrantedAuthority> authorities) {
        log.debug("Create authentication for user: {}", userPrincipal.username());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserPrincipal createUserPrincipal(Claims claims) {
        log.debug("Create user principal from claims");
        Long userId = Long.decode(claims.getSubject());
        String username = claims.get("username", String.class);

        return new UserPrincipal(userId, username, getAuthorities(claims));
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        log.debug("Get authorities from claims");
        List<String> roles = claims.get("roles", List.class);
        ensureRoleExists(roles);

        return roles.stream().map(SimpleGrantedAuthority::new).toList();

    }

    private void ensureRoleExists(List<String> roles) {
        log.debug("Checking if roles: {} exist", roles);
        if (roles == null || roles.isEmpty()) {
            throw new JwtException("Token roles claim is missing");
        }
    }

    private Claims parseToken(String header) {
        log.debug("Parse token from header");
        String token = header.substring(7);
        return jwtTokenProvider.parseToken(token);
    }

    private boolean checkHeader(String header) {
        log.debug("Check valid Authorization header");
        return header != null && header.startsWith("Bearer ");
    }
}
