package io.github.artsobol.fitnessapi.feature.training.dto.response;

import io.github.artsobol.fitnessapi.feature.training.entity.TrainingStatus;
import io.github.artsobol.fitnessapi.feature.user.dto.response.UserResponse;

import java.time.Instant;

public record TrainingSessionResponse(
        Long id,
        UserResponse user,
        TrainingResponse training,
        TrainingStatus trainingStatus,
        Instant startedAt,
        Instant completedAt
) {
}
