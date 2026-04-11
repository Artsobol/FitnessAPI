package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.training.dto.request.CreateTrainingRatingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.request.UpdateTrainingRatingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingRatingResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.Training;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingRating;
import io.github.artsobol.fitnessapi.feature.training.mapper.TrainingRatingMapper;
import io.github.artsobol.fitnessapi.feature.training.repository.TrainingRatingRepository;
import io.github.artsobol.fitnessapi.feature.user.entity.User;
import io.github.artsobol.fitnessapi.feature.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingRatingServiceImpl implements TrainingRatingService {

    private final TrainingRatingRepository trainingRatingRepository;
    private final TrainingRatingMapper trainingRatingMapper;
    private final TrainingFinder trainingFinder;
    private final UserFinder userFinder;

    @Override
    @Transactional(readOnly = true)
    public List<TrainingRatingResponse> getTrainingRatings(Long trainingId) {
        log.debug("Get all ratings trainingId={}", trainingId);
        return trainingRatingRepository.findByTrainingId(trainingId).stream().map(trainingRatingMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public TrainingRatingResponse create(Long trainingId, Long userId, CreateTrainingRatingRequest request) {
        log.info("Create rating trainingId={} userId={}", trainingId, userId);
        User user = userFinder.findById(userId);
        Training training = trainingFinder.findByIdOrThrow(trainingId);
        TrainingRating entity = TrainingRating.create(training, user, request.rating(), request.comment());
        trainingRatingRepository.save(entity);

        return trainingRatingMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TrainingRatingResponse update(Long trainingId, Long userId, UpdateTrainingRatingRequest request) {
        log.info("Update rating trainingId={} userId={}", trainingId, userId);
        TrainingRating entity = findByTrainingIdAndUserId(trainingId, userId);
        if (request.rating() != null) {
            entity.changeRating(request.rating());
        }
        if (request.comment() != null) {
            entity.changeComment(request.comment());
        }

        return trainingRatingMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(Long trainingId, Long userId) {
        log.info("Delete rating trainingId={} userId={}", trainingId, userId);
        TrainingRating entity = findByTrainingIdAndUserId(trainingId, userId);
        trainingRatingRepository.delete(entity);
    }

    private TrainingRating findByTrainingIdAndUserId(Long trainingId, Long userId) {
        return trainingRatingRepository.findByTrainingIdAndUserId(trainingId, userId).orElseThrow(
                () ->  new NotFoundException("{training.rating.not.found}", trainingId, userId)
        );
    }
}
