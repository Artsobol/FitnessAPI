package io.github.artsobol.fitnessapi.feature.refreshtoken.dto.response;

import io.github.artsobol.fitnessapi.feature.refreshtoken.entity.RefreshToken;

public record CreatedRefreshToken(
        String rawToken,
        RefreshToken refreshToken
) {
}
