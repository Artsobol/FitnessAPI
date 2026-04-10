package io.github.artsobol.fitnessapi.feature.type.service;

import io.github.artsobol.fitnessapi.feature.type.entity.Type;

public interface TypeFinder {

    Type findBySlugOrThrow(String slug);
}
