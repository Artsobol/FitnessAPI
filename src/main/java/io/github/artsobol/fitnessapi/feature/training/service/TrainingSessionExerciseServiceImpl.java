package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.exception.http.BadRequestException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingSessionExerciseResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingSession;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingSessionExercise;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingStatus;
import io.github.artsobol.fitnessapi.feature.training.mapper.TrainingSessionExerciseMapper;
import io.github.artsobol.fitnessapi.feature.training.repository.TrainingSessionExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingSessionExerciseServiceImpl implements TrainingSessionExerciseService {

    private final TrainingSessionExerciseRepository trainingSessionExerciseRepository;
    private final TrainingSessionExerciseMapper trainingSessionExerciseMapper;
    private final TrainingSessionFinder trainingSessionFinder;

    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionExerciseResponse> getAllByTrainingSession(Long trainingSessionId, Long userId) {
        trainingSessionFinder.findByIdOrThrow(trainingSessionId, userId);

        return trainingSessionExerciseRepository
                .findByTrainingSessionIdAndTrainingSessionUserIdOrderByTrainingExerciseOrderIndexAsc(
                        trainingSessionId,
                        userId
                )
                .stream()
                .map(trainingSessionExerciseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TrainingSessionExerciseResponse getById(Long trainingSessionExerciseId, Long userId) {
        return trainingSessionExerciseMapper.toResponse(findByIdOrThrow(trainingSessionExerciseId, userId));
    }

    @Override
    @Transactional
    public TrainingSessionExerciseResponse start(Long trainingSessionExerciseId, Long userId) {
        log.info("Start training session exercise id={} userId={}", trainingSessionExerciseId, userId);
        TrainingSessionExercise entity = findByIdOrThrow(trainingSessionExerciseId, userId);

        ensureSessionInProgress(entity.getTrainingSession());
        ensureExerciseNotFinished(entity);
        entity.start();

        return trainingSessionExerciseMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TrainingSessionExerciseResponse complete(Long trainingSessionExerciseId, Long userId) {
        log.info("Complete training session exercise id={} userId={}", trainingSessionExerciseId, userId);
        TrainingSessionExercise entity = findByIdOrThrow(trainingSessionExerciseId, userId);

        ensureSessionInProgress(entity.getTrainingSession());
        ensureExerciseNotFinished(entity);
        entity.complete();

        return trainingSessionExerciseMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TrainingSessionExerciseResponse skip(Long trainingSessionExerciseId, Long userId) {
        log.info("Skip training session exercise id={} userId={}", trainingSessionExerciseId, userId);
        TrainingSessionExercise entity = findByIdOrThrow(trainingSessionExerciseId, userId);

        ensureSessionInProgress(entity.getTrainingSession());
        ensureExerciseNotFinished(entity);
        entity.skip();

        return trainingSessionExerciseMapper.toResponse(entity);
    }

    private TrainingSessionExercise findByIdOrThrow(Long id, Long userId) {
        return trainingSessionExerciseRepository.findByIdAndTrainingSessionUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("{training.session.exercise.id.not.found}", id));
    }

    private void ensureSessionInProgress(TrainingSession trainingSession) {
        if (trainingSession.getTrainingStatus() != TrainingStatus.IN_PROGRESS) {
            throw new BadRequestException("{training.session.already.finished}", trainingSession.getId());
        }
    }

    private void ensureExerciseNotFinished(TrainingSessionExercise entity) {
        if (entity.isFinished()) {
            throw new BadRequestException("{training.session.exercise.already.finished}", entity.getId());
        }
    }
}
