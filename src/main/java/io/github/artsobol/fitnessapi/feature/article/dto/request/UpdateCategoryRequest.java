package io.github.artsobol.fitnessapi.feature.article.dto.request;

import io.github.artsobol.fitnessapi.infrastructure.validation.annotation.NullOrNotBlank;
import io.github.artsobol.fitnessapi.infrastructure.validation.annotation.Slug;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        @NullOrNotBlank(message = "{category.name.blank}")
        String name,
        @Slug(message = "{category.slug.invalid}")
        @Size(max = 40, message = "{category.slug.size}")
        @NullOrNotBlank(message = "{category.slug.blank}")
        String slug
) {
}
