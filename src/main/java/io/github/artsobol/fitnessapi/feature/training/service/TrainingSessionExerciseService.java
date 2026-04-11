package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingSessionExerciseResponse;

import java.util.List;

public interface TrainingSessionExerciseService {

    List<TrainingSessionExerciseResponse> getAllByTrainingSession(Long trainingSessionId, Long userId);

    TrainingSessionExerciseResponse getById(Long trainingSessionExerciseId, Long userId);

    TrainingSessionExerciseResponse start(Long trainingSessionExerciseId, Long userId);

    TrainingSessionExerciseResponse complete(Long trainingSessionExerciseId, Long userId);

    TrainingSessionExerciseResponse skip(Long trainingSessionExerciseId, Long userId);
}
