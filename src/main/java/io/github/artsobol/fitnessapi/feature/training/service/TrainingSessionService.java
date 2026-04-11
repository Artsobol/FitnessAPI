package io.github.artsobol.fitnessapi.feature.training.service;

import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingSessionResponse;

import java.util.List;

public interface TrainingSessionService {

    List<TrainingSessionResponse> getAllByUser(Long userId);

    TrainingSessionResponse getById(Long sessionId, Long userId);

    TrainingSessionResponse create(Long trainingId, Long userId);

    TrainingSessionResponse complete(Long sessionId, Long userId);

    TrainingSessionResponse abandon(Long sessionId, Long userId);
}
