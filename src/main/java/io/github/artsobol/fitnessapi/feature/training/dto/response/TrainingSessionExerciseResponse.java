package io.github.artsobol.fitnessapi.feature.training.dto.response;

import io.github.artsobol.fitnessapi.feature.training.entity.ExerciseStatus;

import java.time.Instant;

public record TrainingSessionExerciseResponse(
        Long id,
        TrainingSessionResponse trainingSession,
        TrainingExerciseResponse trainingExercise,
        ExerciseStatus exerciseStatus,
        Instant completedAt
) {
}
