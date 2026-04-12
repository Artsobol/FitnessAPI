package io.github.artsobol.fitnessapi.feature.auth.auth.web;

import io.github.artsobol.fitnessapi.config.properties.security.CookieProperties;
import io.github.artsobol.fitnessapi.feature.auth.auth.dto.request.RegistrationRequest;
import io.github.artsobol.fitnessapi.feature.auth.auth.dto.request.SessionMetadata;
import io.github.artsobol.fitnessapi.feature.auth.auth.dto.response.AuthResponse;
import io.github.artsobol.fitnessapi.feature.auth.auth.service.RegistrationService;
import io.github.artsobol.fitnessapi.feature.auth.auth.support.DeviceInfo;
import io.github.artsobol.fitnessapi.feature.auth.auth.support.UserAgentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final CookieProperties properties;
    private final RegistrationService registrationService;
    private final UserAgentService userAgentService;

    @PostMapping
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegistrationRequest registrationRequest,
            HttpServletRequest servletRequest
    ) {
        String userAgent = servletRequest.getHeader(HttpHeaders.USER_AGENT);
        String ipAddress = servletRequest.getRemoteAddr();
        DeviceInfo deviceInfo = getDeviceInfo(userAgent);
        log.debug(
                "Received registration request username={} device={}",
                registrationRequest.username(),
                deviceInfo.device()
        );

        AuthResponse authResponse = registrationService.register(
                registrationRequest,
                getSessionMetadata(ipAddress, userAgent, deviceInfo)
        );
        log.debug("Registration finished username={}", registrationRequest.username());

        return getResponse(getResponseCookie(authResponse), authResponse);
    }

    private DeviceInfo getDeviceInfo(String userAgent) {
        return userAgentService.parse(userAgent);
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

    private static SessionMetadata getSessionMetadata(String ipAddress, String userAgent, DeviceInfo deviceInfo) {
        return new SessionMetadata(ipAddress, userAgent, deviceInfo.device());
    }

    private static ResponseEntity<AuthResponse> getResponse(ResponseCookie responseCookie, AuthResponse authResponse) {
        return ResponseEntity.status(201).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(authResponse);
    }
}
