package io.github.artsobol.fitnessapi.feature.exercise.web;

import io.github.artsobol.fitnessapi.feature.exercise.dto.request.CreateExerciseRequest;
import io.github.artsobol.fitnessapi.feature.exercise.dto.request.UpdateExerciseRequest;
import io.github.artsobol.fitnessapi.feature.exercise.dto.response.ExerciseResponse;
import io.github.artsobol.fitnessapi.feature.exercise.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService service;

    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getAll() {
        List<ExerciseResponse> response = service.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResponse> getById(@PathVariable Long exerciseId) {
        ExerciseResponse response = service.getById(exerciseId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ExerciseResponse> update(

            @RequestBody @Valid CreateExerciseRequest request
    ) {
        ExerciseResponse response = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResponse> create(
            @PathVariable Long exerciseId,
            @RequestBody @Valid UpdateExerciseRequest request
    ) {
        ExerciseResponse response = service.update(exerciseId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{exerciseId}/videos/{videoId}/add")
    public ResponseEntity<ExerciseResponse> addVideo(@PathVariable Long exerciseId, @PathVariable Long videoId) {
        ExerciseResponse response = service.addVideo(exerciseId, videoId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{exerciseId}/videos/{videoId}/remove")
    public ResponseEntity<ExerciseResponse> removeVideo(@PathVariable Long exerciseId, @PathVariable Long videoId) {
        ExerciseResponse response = service.removeVideo(exerciseId, videoId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> delete(@PathVariable Long exerciseId) {
        service.deactivate(exerciseId);

        return ResponseEntity.noContent().build();
    }
}
