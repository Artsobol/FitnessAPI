package io.github.artsobol.fitnessapi.feature.training.dto.request;

import io.github.artsobol.fitnessapi.feature.training.entity.TrainingLevel;
import jakarta.validation.constraints.Size;

public record UpdateTrainingRequest(
        @Size(max = 50, message = "{training.title.size}")
        String title,
        String description,
        TrainingLevel trainingLevel
) {
}
