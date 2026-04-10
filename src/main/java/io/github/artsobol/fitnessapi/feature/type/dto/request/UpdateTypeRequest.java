package io.github.artsobol.fitnessapi.feature.type.dto.request;

import io.github.artsobol.fitnessapi.infrastructure.validation.annotation.Slug;
import jakarta.validation.constraints.Size;

public record UpdateTypeRequest(
        @Size(max = 30, message = "{type.name.size}")
        String name,
        @Slug
        @Size(max = 30, message = "{type.slug.size}")
        String slug
) {
}
