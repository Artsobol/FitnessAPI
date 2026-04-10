package io.github.artsobol.fitnessapi.feature.tag.service;

import io.github.artsobol.fitnessapi.feature.tag.entity.Tag;

public interface TagFinder {

    Tag findBySlugOrThrow(String slug);
}
