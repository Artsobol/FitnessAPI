package io.github.artsobol.fitnessapi.feature.category.service;

import io.github.artsobol.fitnessapi.feature.category.dto.request.CreateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.category.dto.request.UpdateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.category.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);

    CategoryResponse getBySlug(String slug);

    CategoryResponse create(CreateCategoryRequest request);

    CategoryResponse update(String slug, UpdateCategoryRequest request);

    void delete(String slug);
}
