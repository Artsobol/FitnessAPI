package io.github.artsobol.fitnessapi.feature.article.service;

import io.github.artsobol.fitnessapi.feature.article.entity.Category;

public interface CategoryFinder {

    Category findByIdOrThrow(Long id);
}
