package io.github.artsobol.fitnessapi.feature.auth.dto.request;

public record SessionMetadata(
        String ipAddress,
        String userAgent,
        String deviceName
) {
}
