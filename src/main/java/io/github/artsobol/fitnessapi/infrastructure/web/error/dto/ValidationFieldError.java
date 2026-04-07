package io.github.artsobol.fitnessapi.infrastructure.web.error.dto;

public record ValidationFieldError(
        String field,
        String message
) {
}
