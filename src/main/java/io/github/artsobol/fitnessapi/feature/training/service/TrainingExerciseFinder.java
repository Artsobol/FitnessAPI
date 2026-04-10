package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.feature.training.entity.TrainingExercise;

public interface TrainingExerciseFinder {

    TrainingExercise findByIdOrThrow(Long id);
}
