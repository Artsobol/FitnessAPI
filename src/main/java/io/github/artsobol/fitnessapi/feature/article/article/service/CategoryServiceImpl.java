package io.github.artsobol.fitnessapi.feature.article.article.service;

import io.github.artsobol.fitnessapi.exception.http.ConflictException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.article.article.dto.request.CreateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.article.article.dto.request.UpdateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.article.article.dto.response.CategoryResponse;
import io.github.artsobol.fitnessapi.feature.article.article.entity.Category;
import io.github.artsobol.fitnessapi.feature.article.article.mapper.CategoryMapper;
import io.github.artsobol.fitnessapi.feature.article.article.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService, CategoryFinder {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Slice<CategoryResponse> getAll(Pageable pageable) {
        log.debug("Fetching categories page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category entity = findByIdOrThrow(id);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getBySlug(String slug) {
        Category entity = findBySlug(slug);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public CategoryResponse create(CreateCategoryRequest request) {
        log.info("Creating category slug={}", request.slug());
        ensureSlugNotExists(request.slug());
        Category entity = Category.create(request.name(), request.slug());
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public CategoryResponse update(String slug, UpdateCategoryRequest request) {
        log.info("Updating category slug={}", slug);
        Category entity = findBySlug(slug);
        validateSlugChange(entity.getSlug(), request.slug());
        entity.applyPatch(request.name(), request.slug());

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(String slug) {
        log.info("Deleting category slug={}", slug);
        Category entity = findBySlug(slug);
        repository.delete(entity);
    }

    private Category findBySlug(String slug) {
        log.debug("Fetching category slug={}", slug);
        return repository.findBySlug(slug).orElseThrow(() -> new NotFoundException("{category.slug.not.found}", slug));
    }

    public Category findByIdOrThrow(Long id) {
        log.debug("Fetching category id={}", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("{category.id.not.found}", id));
    }

    private void validateSlugChange(String currentSlug, String newSlug) {
        if (newSlug != null && !currentSlug.equals(newSlug)) {
            ensureSlugNotExists(newSlug);
        }
    }

    private void ensureSlugNotExists(String slug) {
        log.debug("Checking slug uniqueness slug={}", slug);
        if (repository.existsBySlug((slug))) {
            throw new ConflictException("{category.slug.exists}", slug);
        }
    }
}
