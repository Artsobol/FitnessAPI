package io.github.artsobol.fitnessapi.feature.training.dto.response;

import io.github.artsobol.fitnessapi.feature.tag.dto.response.TagResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingLevel;
import io.github.artsobol.fitnessapi.feature.type.dto.response.TypeResponse;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record TrainingResponse(
        Long id,
        String title,
        String description,
        List<TrainingExerciseResponse> exercises,
        Set<TypeResponse> types,
        Set<TagResponse> tags,
        TrainingLevel trainingLevel,
        Instant createdAt,
        Instant updatedAt
) {
}
