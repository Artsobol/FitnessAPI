package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.exercise.entity.Exercise;
import io.github.artsobol.fitnessapi.feature.exercise.service.ExerciseFinder;
import io.github.artsobol.fitnessapi.feature.tag.entity.Tag;
import io.github.artsobol.fitnessapi.feature.tag.service.TagFinder;
import io.github.artsobol.fitnessapi.feature.training.dto.request.CreateTrainingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.request.UpdateTrainingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.Training;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingExercise;
import io.github.artsobol.fitnessapi.feature.training.mapper.TrainingMapper;
import io.github.artsobol.fitnessapi.feature.training.repository.TrainingExerciseRepository;
import io.github.artsobol.fitnessapi.feature.training.repository.TrainingRepository;
import io.github.artsobol.fitnessapi.feature.type.entity.Type;
import io.github.artsobol.fitnessapi.feature.type.service.TypeFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService, TrainingFinder {

    private final TrainingRepository trainingRepository;
    private final TrainingExerciseRepository trainingExerciseRepository;
    private final TrainingMapper trainingMapper;
    private final ExerciseFinder exerciseFinder;
    private final TagFinder tagFinder;
    private final TypeFinder typeFinder;
    private final TrainingExerciseFinder trainingExerciseFinder;

    @Override
    @Transactional(readOnly = true)
    public TrainingResponse getById(Long id) {
        Training entity = findByIdOrThrow(id);

        return trainingMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingResponse> getAll() {
        log.debug("Find all trainings");
        return trainingRepository.findByIsActiveTrue()
                .stream()
                .map(trainingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TrainingResponse create(CreateTrainingRequest request) {
        log.info("Create training title={}", request.title());
        Training entity = Training.create(request.title(), request.description(), request.trainingLevel());
        trainingRepository.save(entity);

        return trainingMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TrainingResponse update(Long id, UpdateTrainingRequest request) {
        log.info("Update training trainingId={}", id);
        Training entity = findByIdOrThrow(id);
        if (request.title() != null && !request.title().isBlank()) {
            entity.updateTitle(request.title());
        }
        if (request.description() != null) {
            entity.updateDescription(request.description());
        }
        if (request.trainingLevel() != null) {
            entity.setTrainingLevel(request.trainingLevel());
        }

        return trainingMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TrainingResponse addExercise(Long id, Long exerciseId) {
        log.info("Add exercise exerciseId={} fromTrainingId={}", exerciseId, id);
        Training entity = findByIdOrThrow(id);
        Exercise exercise = exerciseFinder.findByIdOrThrow(exerciseId);
        entity.addExercise(exercise);

        return trainingMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void removeExercise(Long id, Long trainingExerciseId) {
        log.info("Remove trainingExerciseId={} fromTrainingId={}", trainingExerciseId, id);
        ensureHasExercise(trainingExerciseId, id);
        Training entity = findByIdOrThrow(id);
        TrainingExercise exercise = trainingExerciseFinder.findByIdOrThrow(trainingExerciseId);
        entity.removeExercise(exercise);
    }

    @Override
    @Transactional
    public TrainingResponse addTag(Long id, String slug) {
        log.info("Add tag slug={} fromTrainingId={}", slug, id);
        Training entity = findByIdOrThrow(id);
        Tag tag = tagFinder.findBySlugOrThrow(slug);
        entity.addTag(tag);

        return trainingMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void removeTag(Long id, String slug) {
        log.info("Remove tag slug={} fromTrainingId={}", slug, id);
        Training entity = findByIdOrThrow(id);
        Tag tag = tagFinder.findBySlugOrThrow(slug);
        entity.removeTag(tag);
    }

    @Override
    @Transactional
    public TrainingResponse addType(Long id, String slug) {
        log.info("Add type slug={} fromTrainingId={}", slug, id);
        Training entity = findByIdOrThrow(id);
        Type type = typeFinder.findBySlugOrThrow(slug);
        entity.addType(type);

        return trainingMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void removeType(Long id, String slug) {
        log.info("Remove type slug={} fromTrainingId={}", slug, id);
        Training entity = findByIdOrThrow(id);
        Type type = typeFinder.findBySlugOrThrow(slug);
        entity.removeType(type);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivate training id={}", id);
        Training entity = findByIdOrThrow(id);
        entity.deactivate();
    }

    @Override
    public Training findByIdOrThrow(Long id) {
        log.debug("Find training id={}", id);
        return trainingRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("{training.id.not.found}", id));
    }

    private void ensureHasExercise(Long trainingExerciseId, Long trainingId) {
        if (!trainingExerciseRepository.existsByIdAndTrainingId(trainingExerciseId, trainingId)) {
            throw new NotFoundException("{training.exercise.not.found}", trainingExerciseId, trainingId);
        }
    }
}
