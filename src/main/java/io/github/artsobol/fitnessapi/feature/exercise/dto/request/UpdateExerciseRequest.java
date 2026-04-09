package io.github.artsobol.fitnessapi.feature.exercise.dto.request;

import io.github.artsobol.fitnessapi.feature.exercise.entity.MuscleGroup;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingLevel;
import jakarta.validation.constraints.Size;

public record UpdateExerciseRequest(
        @Size(max = 40, message = "{exercise.title.size}")
        String title,
        String description,
        MuscleGroup muscleGroup,
        TrainingLevel trainingLevel
) {
}
