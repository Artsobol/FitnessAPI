package io.github.artsobol.fitnessapi.feature.category.service;

import io.github.artsobol.fitnessapi.feature.category.entity.Category;

public interface CategoryFinder {

    Category findByIdOrThrow(Long id);
}
