package io.github.artsobol.fitnessapi.feature.article.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateArticleRequest(
        @NotBlank(message = "{article.title.blank}")
        @Size(max = 100, message = "{article.title.size}")
        String title,
        String description
) {
}
