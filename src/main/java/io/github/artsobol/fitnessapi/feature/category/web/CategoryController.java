package io.github.artsobol.fitnessapi.feature.category.web;

import io.github.artsobol.fitnessapi.feature.category.dto.request.CreateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.category.dto.request.UpdateCategoryRequest;
import io.github.artsobol.fitnessapi.feature.category.dto.response.CategoryResponse;
import io.github.artsobol.fitnessapi.feature.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<CategoryResponse> response = service.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<CategoryResponse> getBySlug(@PathVariable String slug) {
        CategoryResponse response = service.getBySlug(slug);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request) {
        CategoryResponse response = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{slug}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable String slug,
            @RequestBody @Valid UpdateCategoryRequest request
    ) {
        CategoryResponse response = service.update(slug, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable String slug) {
        service.delete(slug);

        return ResponseEntity.noContent().build();
    }
}
