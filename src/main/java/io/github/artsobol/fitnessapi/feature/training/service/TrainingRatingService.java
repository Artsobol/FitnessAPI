package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.feature.training.dto.request.CreateTrainingRatingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.request.UpdateTrainingRatingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingRatingResponse;

import java.util.List;

public interface TrainingRatingService {

    List<TrainingRatingResponse> getTrainingRatings(Long trainingId);

    TrainingRatingResponse create(Long trainingId, Long userId,  CreateTrainingRatingRequest request);

    TrainingRatingResponse update(Long trainingId, Long userId, UpdateTrainingRatingRequest request);

    void delete(Long trainingId, Long userId);
}
