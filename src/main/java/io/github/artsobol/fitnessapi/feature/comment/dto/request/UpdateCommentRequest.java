package io.github.artsobol.fitnessapi.feature.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(
        @NotBlank(message = "{comment.blank}")
        String comment
) {
}
