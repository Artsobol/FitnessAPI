package io.github.artsobol.fitnessapi.feature.training.mapper;

import io.github.artsobol.fitnessapi.config.persistence.MapStructConfig;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingSessionExerciseResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingSessionExercise;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = {TrainingSessionMapper.class, TrainingExerciseMapper.class})
public interface TrainingSessionExerciseMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "trainingSession")
    @Mapping(target = "trainingExercise")
    @Mapping(target = "exerciseStatus")
    @Mapping(target = "completedAt")
    TrainingSessionExerciseResponse toResponse(TrainingSessionExercise entity);
}
