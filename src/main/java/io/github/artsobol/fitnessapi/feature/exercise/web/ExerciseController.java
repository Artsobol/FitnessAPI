package io.github.artsobol.fitnessapi.feature.exercise.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.exercise.dto.request.CreateExerciseRequest;
import io.github.artsobol.fitnessapi.feature.exercise.dto.request.UpdateExerciseRequest;
import io.github.artsobol.fitnessapi.feature.exercise.dto.response.ExerciseResponse;
import io.github.artsobol.fitnessapi.feature.exercise.service.ExerciseService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public SliceResponse<ExerciseResponse> getAll(Pageable pageable) {
        return SliceResponse.from(exerciseService.getAll(pageable));
    }

    @GetMapping("/{exerciseId}")
    public ExerciseResponse getById(@PathVariable @Positive Long exerciseId) {
        return exerciseService.getById(exerciseId);
    }

    @PostMapping
    public ResponseEntity<ExerciseResponse> create(
            @RequestBody @Valid CreateExerciseRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        ExerciseResponse response = exerciseService.create(request, userPrincipal.userId());

        return ResponseEntity.created(UriUtils.buildLocation(response.id())).body(response);
    }

    @PatchMapping("/{exerciseId}")
    public ExerciseResponse update(
            @PathVariable @Positive Long exerciseId,
            @RequestBody @Valid UpdateExerciseRequest request
    ) {
        return exerciseService.update(exerciseId, request);

    }

    @PutMapping("/{exerciseId}/videos/{videoId}")
    public ExerciseResponse addVideo(@PathVariable @Positive Long exerciseId, @PathVariable @Positive  Long videoId) {
        return exerciseService.addVideo(exerciseId, videoId);
    }

    @DeleteMapping("/{exerciseId}/videos/{videoId}")
    public ResponseEntity<Void> removeVideo(@PathVariable @Positive Long exerciseId, @PathVariable @Positive  Long videoId) {
        exerciseService.removeVideo(exerciseId, videoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long exerciseId) {
        exerciseService.deactivate(exerciseId);

        return ResponseEntity.noContent().build();
    }
}
