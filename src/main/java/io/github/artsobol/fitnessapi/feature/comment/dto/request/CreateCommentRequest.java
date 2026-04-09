package io.github.artsobol.fitnessapi.feature.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
        @NotBlank(message = "{comment.blank}")
        String comment
) {
}
