package io.github.artsobol.fitnessapi.feature.training.web;

import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingSessionExerciseResponse;
import io.github.artsobol.fitnessapi.feature.training.service.TrainingSessionExerciseService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
public class TrainingSessionExerciseController {

    private final TrainingSessionExerciseService trainingSessionExerciseService;

    @GetMapping("/training-sessions/{sessionId}/exercises")
    public List<TrainingSessionExerciseResponse> getAllByTrainingSession(
            @PathVariable @Positive Long sessionId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionExerciseService.getAllByTrainingSession(sessionId, principal.userId());
    }

    @GetMapping("/training-session-exercises/{sessionExerciseId}")
    public TrainingSessionExerciseResponse getById(
            @PathVariable @Positive Long sessionExerciseId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionExerciseService.getById(sessionExerciseId, principal.userId());
    }

    @PostMapping("/training-session-exercises/{sessionExerciseId}/start")
    public TrainingSessionExerciseResponse start(
            @PathVariable @Positive Long sessionExerciseId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionExerciseService.start(sessionExerciseId, principal.userId());
    }

    @PostMapping("/training-session-exercises/{sessionExerciseId}/complete")
    public TrainingSessionExerciseResponse complete(
            @PathVariable @Positive Long sessionExerciseId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionExerciseService.complete(sessionExerciseId, principal.userId());
    }

    @PostMapping("/training-session-exercises/{sessionExerciseId}/skip")
    public TrainingSessionExerciseResponse skip(
            @PathVariable @Positive Long sessionExerciseId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return trainingSessionExerciseService.skip(sessionExerciseId, principal.userId());
    }
}
