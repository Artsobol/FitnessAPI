package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingFavouriteResponse;

import java.util.List;

public interface TrainingFavouriteService {

    TrainingFavouriteResponse create(Long userId, Long trainingId);

    List<TrainingFavouriteResponse> getAll(Long userId);

    void delete(Long userId, Long trainingId);
}
