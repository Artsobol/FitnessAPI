package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.feature.training.dto.request.CreateTrainingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.request.UpdateTrainingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingResponse;

import java.util.List;

public interface TrainingService {

    TrainingResponse getById(Long id);

    List<TrainingResponse> getAll();

    TrainingResponse create(CreateTrainingRequest request);

    TrainingResponse update(Long id, UpdateTrainingRequest request);

    TrainingResponse addExercise(Long id, Long trainingExerciseId);

    void removeExercise(Long id, Long trainingExerciseId);

    TrainingResponse addType(Long id, String slug);

    void removeType(Long id, String slug);

    TrainingResponse addTag(Long id, String slug);

    void removeTag(Long id, String slug);

    void deactivate(Long id);
}
