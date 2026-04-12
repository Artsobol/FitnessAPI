package io.github.artsobol.fitnessapi.feature.training.type.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.training.type.dto.request.CreateTypeRequest;
import io.github.artsobol.fitnessapi.feature.training.type.dto.request.UpdateTypeRequest;
import io.github.artsobol.fitnessapi.feature.training.type.dto.response.TypeResponse;
import io.github.artsobol.fitnessapi.feature.training.type.service.TypeService;
import io.github.artsobol.fitnessapi.infrastructure.validation.annotation.Slug;
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

import static io.github.artsobol.fitnessapi.utils.UriUtils.buildLocation;

@Validated
@RestController
@RequestMapping("/types")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping
    public SliceResponse<TypeResponse> getTypes(Pageable pageable) {
        return SliceResponse.from(typeService.getTypes(pageable));
    }

    @GetMapping("/{slug}")
    public TypeResponse getBySlug(@PathVariable @Slug String slug) {
        return typeService.getBySlug(slug);
    }

    @PostMapping
    public ResponseEntity<TypeResponse> create(@RequestBody @Valid CreateTypeRequest request) {
        TypeResponse response = typeService.create(request);

        return ResponseEntity.created(buildLocation(response.slug())).body(response);
    }

    @PatchMapping("/{slug}")
    public TypeResponse update(@PathVariable @Slug String slug, @RequestBody @Valid UpdateTypeRequest request) {
        return typeService.update(slug, request);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable @Slug String slug) {
        typeService.delete(slug);

        return ResponseEntity.noContent().build();
    }
}
