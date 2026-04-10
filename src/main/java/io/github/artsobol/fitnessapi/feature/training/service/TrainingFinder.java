package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.feature.training.entity.Training;

public interface TrainingFinder {

    Training findByIdOrThrow(Long id);
}
