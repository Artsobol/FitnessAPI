package io.github.artsobol.fitnessapi.infrastructure.web.error.dto;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<ValidationFieldError> errors
) {
}
