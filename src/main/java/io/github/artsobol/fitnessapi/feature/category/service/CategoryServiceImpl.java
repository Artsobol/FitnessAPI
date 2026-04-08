package io.github.artsobol.fitnessapi.feature.category.service;

import io.github.artsobol.fitnessapi.exception.http.ConflictException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.category.dto.request.CreateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.category.dto.request.UpdateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.category.dto.response.CategoryResponse;
import io.github.artsobol.fitnessapi.feature.category.entity.Category;
import io.github.artsobol.fitnessapi.feature.category.mapper.CategoryMapper;
import io.github.artsobol.fitnessapi.feature.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService, CategoryFinder {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        log.debug("Finding all categories");
        return repository.findAll().stream().map(mapper::toResponse).toList();
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
        log.info("Creating category with slug: {}", request.slug());
        ensureSlugNotExists(request.slug());
        Category entity = Category.create(request.name(), request.slug());
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public CategoryResponse update(String slug, UpdateCategoryRequest request) {
        log.info("Updating category with slug: {}", slug);
        Category entity = findBySlug(slug);
        if (request.slug() != null && !entity.getSlug().equals(request.slug())) {
            ensureSlugNotExists(request.slug());
            entity.updateSlug(request.slug());
        }
        if (request.name() != null) {
            entity.updateName(request.name());
        }

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(String slug) {
        log.info("Deleting category with slug: {}", slug);
        Category entity = findBySlug(slug);
        repository.delete(entity);
    }

    private Category findBySlug(String slug) {
        log.debug("Finding category with slug: {}", slug);
        return repository.findBySlug(slug).orElseThrow(() -> new NotFoundException("{category.slug.not.found}", slug));
    }

    public Category findByIdOrThrow(Long id) {
        log.debug("Finding category with id: {}", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("{category.id.not.found}", id));
    }

    private void ensureSlugNotExists(String slug) {
        log.debug("Checking slug not exists");
        if (repository.existsBySlug((slug))) {
            throw new ConflictException("{category.slug.exists}", slug);
        }
    }
}
