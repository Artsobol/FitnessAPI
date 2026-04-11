package io.github.artsobol.fitnessapi.feature.training.mapper;

import io.github.artsobol.fitnessapi.config.persistence.MapStructConfig;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingFavouriteResponse;
import io.github.artsobol.fitnessapi.feature.training.entity.TrainingFavourite;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = {TrainingMapper.class})
public interface TrainingFavouriteMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "training")
    @Mapping(target = "createdAt")
    TrainingFavouriteResponse toResponse(TrainingFavourite entity);
}
