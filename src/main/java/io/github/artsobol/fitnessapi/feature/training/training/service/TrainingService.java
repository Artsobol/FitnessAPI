package io.github.artsobol.fitnessapi.feature.training.training.service;

import io.github.artsobol.fitnessapi.feature.training.training.dto.request.CreateTrainingRequest;
import io.github.artsobol.fitnessapi.feature.training.training.dto.request.UpdateTrainingRequest;
import io.github.artsobol.fitnessapi.feature.training.training.dto.response.TrainingResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface TrainingService {

    TrainingResponse getById(Long id);

    Slice<TrainingResponse> getAll(Pageable pageable);

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
