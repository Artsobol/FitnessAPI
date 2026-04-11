package io.github.artsobol.fitnessapi.feature.training.repository;

import io.github.artsobol.fitnessapi.feature.training.entity.TrainingSessionExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingSessionExerciseRepository extends JpaRepository<TrainingSessionExercise, Long> {

    List<TrainingSessionExercise> findByTrainingSessionIdAndTrainingSessionUserIdOrderByTrainingExerciseOrderIndexAsc(
            Long trainingSessionId,
            Long userId
    );

    Optional<TrainingSessionExercise> findByIdAndTrainingSessionUserId(Long id, Long userId);
}
