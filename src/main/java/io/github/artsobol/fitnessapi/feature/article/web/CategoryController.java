package io.github.artsobol.fitnessapi.feature.article.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.article.dto.request.CreateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.request.UpdateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.response.CategoryResponse;
import io.github.artsobol.fitnessapi.feature.article.service.CategoryService;
import io.github.artsobol.fitnessapi.infrastructure.validation.annotation.Slug;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public SliceResponse<CategoryResponse> getAll(Pageable pageable) {
        return SliceResponse.from(categoryService.getAll(pageable));
    }

    @GetMapping("/{slug}")
    public CategoryResponse getBySlug(@PathVariable @Slug String slug) {
        return categoryService.getBySlug(slug);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request) {
        CategoryResponse response = categoryService.create(request);

        return ResponseEntity.created(UriUtils.buildLocation(response.slug())).body(response);
    }

    @PatchMapping("/{slug}")
    public CategoryResponse update(
            @PathVariable @Slug String slug,
            @RequestBody @Valid UpdateCategoryRequest request
    ) {
        return categoryService.update(slug, request);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable @Slug String slug) {
        categoryService.delete(slug);

        return ResponseEntity.noContent().build();
    }
}
