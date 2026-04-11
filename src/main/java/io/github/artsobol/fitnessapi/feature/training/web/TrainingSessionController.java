package io.github.artsobol.fitnessapi.feature.training.web;

import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingSessionResponse;
import io.github.artsobol.fitnessapi.feature.training.service.TrainingSessionService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    @GetMapping("/training-sessions")
    public List<TrainingSessionResponse> getAllByCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        return trainingSessionService.getAllByUser(principal.userId());
    }

    @GetMapping("/training-sessions/{sessionId}")
    public TrainingSessionResponse getById(
            @PathVariable @Positive Long sessionId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionService.getById(sessionId, principal.userId());
    }

    @PostMapping("/trainings/{trainingId}/sessions")
    public ResponseEntity<TrainingSessionResponse> create(
            @PathVariable @Positive Long trainingId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        TrainingSessionResponse response = trainingSessionService.create(trainingId, principal.userId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/training-sessions/{sessionId}/complete")
    public TrainingSessionResponse complete(
            @PathVariable @Positive Long sessionId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionService.complete(sessionId, principal.userId());
    }

    @PostMapping("/training-sessions/{sessionId}/abandon")
    public TrainingSessionResponse abandon(
            @PathVariable @Positive Long sessionId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionService.abandon(sessionId, principal.userId());
    }
}
