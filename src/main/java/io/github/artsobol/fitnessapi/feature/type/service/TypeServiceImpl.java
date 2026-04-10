package io.github.artsobol.fitnessapi.feature.type.service;

import io.github.artsobol.fitnessapi.exception.http.ConflictException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.type.dto.request.CreateTypeRequest;
import io.github.artsobol.fitnessapi.feature.type.dto.request.UpdateTypeRequest;
import io.github.artsobol.fitnessapi.feature.type.dto.response.TypeResponse;
import io.github.artsobol.fitnessapi.feature.type.entity.Type;
import io.github.artsobol.fitnessapi.feature.type.mapper.TypeMapper;
import io.github.artsobol.fitnessapi.feature.type.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService, TypeFinder {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    @Override
    public Slice<TypeResponse> getTypes(Pageable pageable) {
        log.debug("Find types page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return typeRepository.findAll(pageable).map(typeMapper::toResponse);
    }

    @Override
    public TypeResponse getBySlug(String slug) {
        return typeMapper.toResponse(findBySlugOrThrow(slug));
    }

    @Override
    @Transactional
    public TypeResponse create(CreateTypeRequest request) {
        log.info("Create type slug={}", request.slug());
        ensureSlugNotExists(request.slug());
        Type entity = Type.create(request.name(), request.slug());

        typeRepository.save(entity);
        return typeMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TypeResponse update(String slug, UpdateTypeRequest request) {
        log.info("Find type slug={}", slug);
        Type entity = findBySlugOrThrow(slug);
        if (request.slug() != null && !entity.getSlug().equals(request.slug())) {
            ensureSlugNotExists(request.slug());
        }
        entity.applyUpdate(request.name(), request.slug());

        return typeMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(String slug) {
        log.info("Delete type slug={}", slug);
        Type entity = findBySlugOrThrow(slug);
        typeRepository.delete(entity);
    }

    @Override
    public Type findBySlugOrThrow(String slug) {
        log.debug("Find type slug={}", slug);
        return typeRepository.findBySlug(slug).orElseThrow(
                () -> new NotFoundException("{type.slug.not.found}", slug)
        );
    }

    private void ensureSlugNotExists(String slug) {
        if (typeRepository.existsBySlug(slug)) {
            throw new ConflictException("{type.slug.exists}", slug);
        }
    }
}
