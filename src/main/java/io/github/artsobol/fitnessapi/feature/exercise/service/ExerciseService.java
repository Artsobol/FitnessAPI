package io.github.artsobol.fitnessapi.feature.exercise.service;

import io.github.artsobol.fitnessapi.feature.exercise.dto.request.CreateExerciseRequest;
import io.github.artsobol.fitnessapi.feature.exercise.dto.request.UpdateExerciseRequest;
import io.github.artsobol.fitnessapi.feature.exercise.dto.response.ExerciseResponse;

import java.util.List;

public interface ExerciseService {

    ExerciseResponse getById(Long id);

    List<ExerciseResponse> getAll();

    ExerciseResponse create(CreateExerciseRequest request);

    ExerciseResponse update(Long id, UpdateExerciseRequest request);

    ExerciseResponse addVideo(Long id, Long videoId);

    ExerciseResponse removeVideo(Long id, Long videoId);

    void deactivate(Long id);
}
