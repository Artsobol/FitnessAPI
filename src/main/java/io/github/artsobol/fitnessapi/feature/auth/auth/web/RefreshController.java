package io.github.artsobol.fitnessapi.feature.auth.auth.web;

import io.github.artsobol.fitnessapi.config.properties.security.CookieProperties;
import io.github.artsobol.fitnessapi.feature.auth.auth.dto.response.AuthResponse;
import io.github.artsobol.fitnessapi.feature.auth.auth.service.RefreshService;
import io.github.artsobol.fitnessapi.feature.auth.refreshtoken.dto.request.RotateRefreshTokenRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth/refresh")
@RequiredArgsConstructor
public class RefreshController {

    private final CookieProperties properties;
    private final RefreshService service;

    @PostMapping
    public ResponseEntity<AuthResponse> login(
            @CookieValue(value = "refresh_token", required = false) String refreshToken,
            HttpServletRequest servletRequest
    ) {
        String userAgent = servletRequest.getHeader(HttpHeaders.USER_AGENT);
        String ipAddress = servletRequest.getRemoteAddr();
        log.debug("Receiving refresh request device={}", userAgent);

        RotateRefreshTokenRequest request = new RotateRefreshTokenRequest(refreshToken, ipAddress, userAgent);
        AuthResponse authResponse = service.refresh(request);
        log.debug("Refresh request finished");

        return getResponse(getResponseCookie(authResponse), authResponse);
    }

    private @NonNull ResponseCookie getResponseCookie(AuthResponse response) {
        return ResponseCookie.from(properties.cookieName(), response.refreshToken())
                .httpOnly(true)
                .secure(properties.secure())
                .sameSite(properties.sameSite())
                .path(properties.path())
                .maxAge(properties.maxAge())
                .build();
    }

    private static ResponseEntity<AuthResponse> getResponse(ResponseCookie responseCookie, AuthResponse authResponse) {
        return ResponseEntity.status(201).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(authResponse);
    }
}
