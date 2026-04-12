package io.github.artsobol.fitnessapi.feature.exercise.dto.response;

import io.github.artsobol.fitnessapi.feature.exercise.entity.MuscleGroup;
import io.github.artsobol.fitnessapi.feature.training.training.entity.TrainingLevel;
import io.github.artsobol.fitnessapi.feature.video.dto.response.VideoResponse;

import java.time.Instant;
import java.util.Set;

public record ExerciseResponse(
        Long id,
        String title,
        String description,
        Set<VideoResponse> videos,
        MuscleGroup muscleGroup,
        TrainingLevel trainingLevel,
        Instant createdAt,
        Instant updatedAt
) {
}
