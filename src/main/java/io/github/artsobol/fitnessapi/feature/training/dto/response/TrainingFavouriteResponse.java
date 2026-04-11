package io.github.artsobol.fitnessapi.feature.training.dto.response;

import java.time.Instant;

public record TrainingFavouriteResponse(
        TrainingResponse training, Instant createdAt
) {
}
