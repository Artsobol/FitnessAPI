package io.github.artsobol.fitnessapi.feature.training.repository;

import io.github.artsobol.fitnessapi.feature.training.entity.TrainingExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Long> {

    boolean existsByIdAndTrainingId(Long id, Long trainingId);
}
