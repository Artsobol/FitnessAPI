package io.github.artsobol.fitnessapi.feature.category.dto.request;

import io.github.artsobol.fitnessapi.infrastructure.validation.annotation.Slug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        @NotBlank(message = "{category.name.blank}")
        String name,
        @Slug(message = "{category.slug.invalid}")
        @Size(max = 40, message = "{category.slug.size}")
        String slug
) {
}
