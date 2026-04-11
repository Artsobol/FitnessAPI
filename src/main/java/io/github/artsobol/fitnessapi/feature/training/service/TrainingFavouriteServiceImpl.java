package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingFavouriteResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.Training;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingFavourite;
import io.github.artsobol.fitnessapi.feature.training.mapper.TrainingFavouriteMapper;
import io.github.artsobol.fitnessapi.feature.training.repository.TrainingFavouriteRepository;
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
public class TrainingFavouriteServiceImpl implements TrainingFavouriteService {

    private final TrainingFavouriteRepository trainingFavouriteRepository;
    private final TrainingFavouriteMapper trainingFavouriteMapper;
    private final UserFinder userFinder;
    private final TrainingFinder trainingFinder;

    @Override
    @Transactional
    public TrainingFavouriteResponse create(Long userId, Long trainingId) {
        log.info("Create favourite training trainingId={}, userId={}", trainingId, userId);
        User user = userFinder.findById(userId);
        Training training = trainingFinder.findByIdOrThrow(trainingId);
        TrainingFavourite entity = TrainingFavourite.create(training, user);
        trainingFavouriteRepository.save(entity);

        return trainingFavouriteMapper.toResponse(entity);
    }

    @Override
    public List<TrainingFavouriteResponse> getAll(Long userId) {
        log.debug("Find all ratings userId={}", userId);
        return trainingFavouriteRepository.findByUserId(userId)
                .stream()
                .map(trainingFavouriteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long userId, Long trainingId) {
        log.info("Delete favourite training trainingId={} userId={}",  trainingId, userId);
        TrainingFavourite entity = trainingFavouriteRepository.findByUserIdAndTrainingId(userId, trainingId).orElseThrow(
                () -> new NotFoundException("{training.favourite.not.found}", userId, trainingId)
        );
        trainingFavouriteRepository.delete(entity);
    }
}
