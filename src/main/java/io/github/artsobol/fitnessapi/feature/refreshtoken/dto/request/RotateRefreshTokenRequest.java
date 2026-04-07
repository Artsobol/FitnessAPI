package io.github.artsobol.fitnessapi.feature.refreshtoken.dto.request;

public record RotateRefreshTokenRequest(
        String rawRefreshToken,
        String ipAddress,
        String userAgent
) {
}
