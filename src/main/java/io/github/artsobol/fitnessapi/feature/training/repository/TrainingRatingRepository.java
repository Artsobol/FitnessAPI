package io.github.artsobol.fitnessapi.feature.training.repository;

import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingRatingResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingFavouriteId;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingRatingRepository extends JpaRepository<TrainingRating, TrainingFavouriteId> {

    Optional<TrainingRating> findByTrainingIdAndUserId(Long trainingId, Long userId);

    List<TrainingRating> findByTrainingId(Long trainingId);
}
