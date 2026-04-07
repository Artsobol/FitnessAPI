package io.github.artsobol.fitnessapi.feature.video.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UpdateVideoRequest(
        @Size(max=100, message = "{video.title.long}")
        String title,
        @URL(message = "video.url.invalid")
        String url
) {
}
