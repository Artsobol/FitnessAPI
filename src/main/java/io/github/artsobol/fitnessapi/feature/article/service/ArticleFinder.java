package io.github.artsobol.fitnessapi.feature.article.service;

import io.github.artsobol.fitnessapi.feature.article.entity.Article;

public interface ArticleFinder {

    Article findByIdOrThrow(Long id);
}
