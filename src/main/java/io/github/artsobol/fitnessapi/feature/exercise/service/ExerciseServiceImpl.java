package io.github.artsobol.fitnessapi.feature.exercise.service;

import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.exercise.dto.request.CreateExerciseRequest;
import io.github.artsobol.fitnessapi.feature.exercise.dto.request.UpdateExerciseRequest;
import io.github.artsobol.fitnessapi.feature.exercise.dto.response.ExerciseResponse;
import io.github.artsobol.fitnessapi.feature.exercise.entity.Exercise;
import io.github.artsobol.fitnessapi.feature.exercise.mapper.ExerciseMapper;
import io.github.artsobol.fitnessapi.feature.exercise.repository.ExerciseRepository;
import io.github.artsobol.fitnessapi.feature.video.entity.Video;
import io.github.artsobol.fitnessapi.feature.video.service.VideoFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService, ExerciseFinder {

    private final ExerciseRepository repository;
    private final ExerciseMapper mapper;
    private final VideoFinder videoFinder;

    @Override
    @Transactional(readOnly = true)
    public ExerciseResponse getById(Long id) {
        Exercise entity = findByIdOrThrow(id);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseResponse> getAll() {
        log.debug("Find all exercise");
        return repository.findByIsActiveTrue().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExerciseResponse create(CreateExerciseRequest request) {
        log.info("Create exercise title={}", request.title());
        Exercise entity = Exercise.create(
                request.title(),
                request.description(),
                request.muscleGroup(),
                request.trainingLevel()
        );
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ExerciseResponse update(Long id, UpdateExerciseRequest request) {
        log.info("Update exercise exerciseId={}", id);
        Exercise entity = findByIdOrThrow(id);
        if (request.title() != null && !request.title().isBlank()) {
            entity.updateTitle(request.title());
        }
        if (request.description() != null) {
            entity.updateDescription(request.description());
        }
        if (request.muscleGroup() != null) {
            entity.setMuscleGroup(request.muscleGroup());
        }
        if (request.trainingLevel() != null) {
            entity.setTrainingLevel(request.trainingLevel());
        }

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ExerciseResponse addVideo(Long id, Long videoId) {
        log.info("Add video videoId={} fromExerciseId={}", videoId, id);
        Exercise entity = findByIdOrThrow(id);
        Video video = videoFinder.findByIdOrThrow(videoId);
        entity.addVideo(video);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void removeVideo(Long id, Long videoId) {
        log.info("Remove video videoId={} fromExerciseId={}", videoId, id);
        Exercise entity = findByIdOrThrow(id);
        Video video = videoFinder.findByIdOrThrow(videoId);
        entity.removeVideo(video);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivate exercise id={}", id);
        Exercise entity = findByIdOrThrow(id);
        entity.deactivate();
    }

    @Override
    public Exercise findByIdOrThrow(Long id) {
        log.debug("Find exercise id={}", id);
        return repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("{exercise.id.not.found}", id));
    }
}
