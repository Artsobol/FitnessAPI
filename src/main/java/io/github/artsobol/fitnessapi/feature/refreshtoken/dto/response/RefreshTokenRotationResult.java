package io.github.artsobol.fitnessapi.feature.refreshtoken.dto.response;

import io.github.artsobol.fitnessapi.feature.user.entity.User;

public record RefreshTokenRotationResult(
        User user,
        String rawRefreshToken
) {
}
