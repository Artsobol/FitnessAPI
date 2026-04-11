package io.github.artsobol.fitnessapi.feature.training.repository;

import io.github.artsobol.fitnessapi.feature.training.entity.TrainingFavourite;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingFavouriteRepository extends JpaRepository<TrainingFavourite, TrainingUserId> {

    Optional<TrainingFavourite> findByUserIdAndTrainingId(Long userId, Long trainingId);

    List<TrainingFavourite> findByUserId(Long userId);
}
