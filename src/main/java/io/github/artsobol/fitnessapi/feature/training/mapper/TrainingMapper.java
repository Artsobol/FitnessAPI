package io.github.artsobol.fitnessapi.feature.training.mapper;

import io.github.artsobol.fitnessapi.config.persistence.MapStructConfig;
import io.github.artsobol.fitnessapi.feature.tag.mapper.TagMapper;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.Training;
import io.github.artsobol.fitnessapi.feature.type.mapper.TypeMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = {TrainingExerciseMapper.class, TagMapper.class, TypeMapper.class})
public interface TrainingMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "exercises")
    @Mapping(target = "tags")
    @Mapping(target = "types")
    @Mapping(target = "trainingLevel")
    @Mapping(target = "createdAt")
    @Mapping(target = "updatedAt")
    TrainingResponse toResponse(Training entity);
}
