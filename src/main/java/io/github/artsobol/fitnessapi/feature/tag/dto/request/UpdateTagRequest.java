package io.github.artsobol.fitnessapi.feature.tag.dto.request;

import io.github.artsobol.fitnessapi.infrastructure.validation.annotation.Slug;
import jakarta.validation.constraints.Size;

public record UpdateTagRequest(
        @Size(max = 30, message = "{tag.name.size}")
        String name,
        @Slug
        @Size(max = 30, message = "{tag.slug.size}")
        String slug
) {
}
