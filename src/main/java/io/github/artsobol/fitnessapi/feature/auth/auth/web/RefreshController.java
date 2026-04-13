package io.github.artsobol.fitnessapi.feature.auth.auth.web;

import io.github.artsobol.fitnessapi.config.properties.security.CookieProperties;
import io.github.artsobol.fitnessapi.feature.auth.auth.dto.response.AuthResponse;
import io.github.artsobol.fitnessapi.feature.auth.auth.service.RefreshService;
import io.github.artsobol.fitnessapi.feature.auth.refreshtoken.dto.request.RotateRefreshTokenRequest;
import io.github.artsobol.fitnessapi.infrastructure.web.error.dto.ErrorResponse;
import io.github.artsobol.fitnessapi.infrastructure.web.error.dto.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth")
@RequestMapping("/auth/refresh")
@RequiredArgsConstructor
public class RefreshController {

    private final CookieProperties properties;
    private final RefreshService service;

    @PostMapping
    @Operation(summary = "Refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
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
