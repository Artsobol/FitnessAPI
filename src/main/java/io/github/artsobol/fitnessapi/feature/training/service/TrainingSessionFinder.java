package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.feature.training.entity.TrainingSession;

public interface TrainingSessionFinder {

    TrainingSession findByIdOrThrow(Long id, Long userId);
}
