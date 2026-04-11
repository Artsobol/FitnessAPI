package io.github.artsobol.fitnessapi.feature.user.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Size(max = 30, message = "profile.firstName.long")
        String firstName,

        @Size(max = 30, message = "profile.lastName.long")
        String lastName
) {
}
