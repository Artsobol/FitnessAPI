package io.github.artsobol.fitnessapi.feature.tag.service;

import io.github.artsobol.fitnessapi.exception.http.ConflictException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.tag.dto.request.CreateTagRequest;
import io.github.artsobol.fitnessapi.feature.tag.dto.request.UpdateTagRequest;
import io.github.artsobol.fitnessapi.feature.tag.dto.response.TagResponse;
import io.github.artsobol.fitnessapi.feature.tag.entity.Tag;
import io.github.artsobol.fitnessapi.feature.tag.mapper.TagMapper;
import io.github.artsobol.fitnessapi.feature.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService, TagFinder {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public Slice<TagResponse> getTags(Pageable pageable) {
        log.debug("Find tags page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return tagRepository.findAll(pageable).map(tagMapper::toResponse);
    }

    @Override
    public TagResponse getBySlug(String slug) {
        return tagMapper.toResponse(findBySlugOrThrow(slug));
    }

    @Override
    @Transactional
    public TagResponse create(CreateTagRequest request) {
        log.info("Create tag slug={}", request.slug());
        ensureSlugNotExists(request.slug());
        Tag entity = Tag.create(request.name(), request.slug());

        tagRepository.save(entity);
        return tagMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public TagResponse update(String slug, UpdateTagRequest request) {
        log.info("Find tag slug={}", slug);
        Tag entity = findBySlugOrThrow(slug);
        if (request.slug() != null && !entity.getSlug().equals(request.slug())) {
            ensureSlugNotExists(request.slug());
        }
        entity.applyUpdate(request.name(), request.slug());

        return tagMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(String slug) {
        log.info("Delete tag slug={}", slug);
        Tag entity = findBySlugOrThrow(slug);
        tagRepository.delete(entity);
    }

    @Override
    public Tag findBySlugOrThrow(String slug) {
        log.debug("Find tag slug={}", slug);
        return tagRepository.findBySlug(slug).orElseThrow(
                () -> new NotFoundException("{tag.slug.not.found}", slug)
        );
    }

    private void ensureSlugNotExists(String slug) {
        if (tagRepository.existsBySlug(slug)) {
            throw new ConflictException("{tag.slug.exists}", slug);
        }
    }
}
