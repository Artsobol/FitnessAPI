package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.exception.http.BadRequestException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingSessionResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.Training;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingSession;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingStatus;
import io.github.artsobol.fitnessapi.feature.training.mapper.TrainingSessionMapper;
import io.github.artsobol.fitnessapi.feature.training.repository.TrainingSessionRepository;
import io.github.artsobol.fitnessapi.feature.user.entity.User;
import io.github.artsobol.fitnessapi.feature.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingSessionServiceImpl implements TrainingSessionService, TrainingSessionFinder {

    private final TrainingSessionRepository trainingSessionRepository;
    private final TrainingSessionMapper trainingSessionMapper;
    private final UserFinder userFinder;
    private final TrainingFinder trainingFinder;

    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionResponse> getAllByUser(Long userId) {
        log.debug("Find training sessions for userId={}", userId);
        return trainingSessionRepository.findByUserIdOrderByStartedAtDesc(userId)
                .stream()
                .map(trainingSessionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TrainingSessionResponse getById(Long sessionId, Long userId) {
        return trainingSessionMapper.toResponse(findByIdOrThrow(sessionId, userId));
    }

    @Override
    @Transactional
    public TrainingSessionResponse create(Long trainingId, Long userId) {
        log.info("Create training session trainingId={} userId={}", trainingId, userId);
        ensureNoActiveSession(trainingId, userId);

        User user = userFinder.findById(userId);
        Training training = trainingFinder.findByIdOrThrow(trainingId);

        TrainingSession entity = TrainingSession.create(user, training);
        trainingSessionRepository.save(entity);

        return trainingSessionMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TrainingSessionResponse complete(Long sessionId, Long userId) {
        log.info("Complete training session sessionId={} userId={}", sessionId, userId);
        TrainingSession entity = findByIdOrThrow(sessionId, userId);

        ensureSessionInProgress(entity);
        ensureAllExercisesFinished(entity);
        entity.complete();

        return trainingSessionMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TrainingSessionResponse abandon(Long sessionId, Long userId) {
        log.info("Abandon training session sessionId={} userId={}", sessionId, userId);
        TrainingSession entity = findByIdOrThrow(sessionId, userId);

        ensureSessionInProgress(entity);
        entity.abandon();

        return trainingSessionMapper.toResponse(entity);
    }

    @Override
    public TrainingSession findByIdOrThrow(Long id, Long userId) {
        log.debug("Find training session id={} userId={}", id, userId);
        return trainingSessionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("{training.session.id.not.found}", id));
    }

    private void ensureNoActiveSession(Long trainingId, Long userId) {
        boolean hasActiveSession = trainingSessionRepository.existsByUserIdAndTrainingIdAndTrainingStatus(
                userId,
                trainingId,
                TrainingStatus.IN_PROGRESS
        );

        if (hasActiveSession) {
            throw new BadRequestException("{training.session.already.in.progress}", trainingId);
        }
    }

    private void ensureSessionInProgress(TrainingSession entity) {
        if (entity.getTrainingStatus() != TrainingStatus.IN_PROGRESS) {
            throw new BadRequestException("{training.session.already.finished}", entity.getId());
        }
    }

    private void ensureAllExercisesFinished(TrainingSession entity) {
        boolean hasIncompleteExercises = entity.getExercises().stream()
                .anyMatch(exercise -> !exercise.isFinished());

        if (hasIncompleteExercises) {
            throw new BadRequestException("{training.session.not.ready.for.complete}", entity.getId());
        }
    }
}
