package io.github.artsobol.fitnessapi.feature.training.session.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.training.session.dto.response.TrainingSessionResponse;
import io.github.artsobol.fitnessapi.feature.training.session.service.TrainingSessionService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    @GetMapping("/training-sessions")
    public SliceResponse<TrainingSessionResponse> getAllByCurrentUser(
            @AuthenticationPrincipal UserPrincipal principal,
            Pageable pageable
    ) {
        return SliceResponse.from(trainingSessionService.getAllByUser(principal.userId(), pageable));
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

        return ResponseEntity.created(UriUtils.buildLocation(response.training().id())).body(response);
    }

    @PatchMapping("/training-sessions/{sessionId}/complete")
    public TrainingSessionResponse complete(
            @PathVariable @Positive Long sessionId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionService.complete(sessionId, principal.userId());
    }

    @PatchMapping("/training-sessions/{sessionId}/abandon")
    public TrainingSessionResponse abandon(
            @PathVariable @Positive Long sessionId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionService.abandon(sessionId, principal.userId());
    }
}
