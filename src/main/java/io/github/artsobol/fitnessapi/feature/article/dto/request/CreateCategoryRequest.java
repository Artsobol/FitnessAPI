package io.github.artsobol.fitnessapi.feature.article.dto.request;

import io.github.artsobol.fitnessapi.infrastructure.validation.annotation.Slug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank(message = "{category.name.blank}")
        @Size(max = 40, message = "{category.name.size}")
        String name,
        @Slug(message = "{category.slug.invalid}")
        @NotBlank(message = "{category.slug.blank}")
        @Size(max = 40, message = "{category.slug.size}")
        String slug
) {
}
