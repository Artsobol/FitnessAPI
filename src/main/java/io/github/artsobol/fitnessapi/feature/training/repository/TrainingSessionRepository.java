package io.github.artsobol.fitnessapi.feature.training.repository;

import io.github.artsobol.fitnessapi.feature.training.entity.TrainingStatus;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    List<TrainingSession> findByUserIdOrderByStartedAtDesc(Long userId);

    Optional<TrainingSession> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndTrainingIdAndTrainingStatus(Long userId, Long trainingId, TrainingStatus trainingStatus);
}
