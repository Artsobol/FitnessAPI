package io.github.artsobol.fitnessapi.feature.training.web;

import io.github.artsobol.fitnessapi.feature.training.dto.request.CreateTrainingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.request.UpdateTrainingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingResponse;
import io.github.artsobol.fitnessapi.feature.training.service.TrainingService;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

import java.util.List;

@Validated
@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public List<TrainingResponse> getAll() {
        return trainingService.getAll();
    }

    @GetMapping("/{trainingId}")
    public TrainingResponse getById(@PathVariable @Positive Long trainingId) {
        return trainingService.getById(trainingId);
    }

    @PostMapping
    public ResponseEntity<TrainingResponse> create(@RequestBody @Valid CreateTrainingRequest request) {
        TrainingResponse response = trainingService.create(request);

        return ResponseEntity.created(UriUtils.buildLocation(response.id())).body(response);
    }

    @PatchMapping("/{trainingId}")
    public TrainingResponse update(
            @PathVariable @Positive Long trainingId,
            @RequestBody @Valid UpdateTrainingRequest request
    ) {
        return trainingService.update(trainingId, request);

    }

    @PutMapping("/{trainingId}/tags/{slug}")
    public TrainingResponse addTag(@PathVariable @Positive Long trainingId, @PathVariable String slug) {
        return trainingService.addTag(trainingId, slug);
    }

    @DeleteMapping("/{trainingId}/tags/{slug}")
    public ResponseEntity<Void> removeTag(@PathVariable @Positive Long trainingId, @PathVariable String   slug) {
        trainingService.removeTag(trainingId, slug);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{trainingId}/types/{slug}")
    public TrainingResponse addType(@PathVariable @Positive Long trainingId, @PathVariable String slug) {
        return trainingService.addType(trainingId, slug);
    }

    @DeleteMapping("/{trainingId}/types/{slug}")
    public ResponseEntity<Void> removeType(@PathVariable @Positive Long trainingId, @PathVariable String slug) {
        trainingService.removeType(trainingId, slug);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{trainingId}/exercises/{exerciseId}")
    public TrainingResponse addExercise(@PathVariable @Positive Long trainingId, @PathVariable @Positive  Long exerciseId) {
        return trainingService.addExercise(trainingId, exerciseId);
    }

    @DeleteMapping("/{trainingId}/exercise-items/{trainingExerciseId}")
    public ResponseEntity<Void> removeExercise(@PathVariable @Positive Long trainingId, @PathVariable @Positive  Long trainingExerciseId) {
        trainingService.removeExercise(trainingId, trainingExerciseId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long trainingId) {
        trainingService.deactivate(trainingId);

        return ResponseEntity.noContent().build();
    }
}
