package io.github.artsobol.fitnessapi.feature.training.tag.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.training.tag.dto.request.CreateTagRequest;
import io.github.artsobol.fitnessapi.feature.training.tag.dto.request.UpdateTagRequest;
import io.github.artsobol.fitnessapi.feature.training.tag.dto.response.TagResponse;
import io.github.artsobol.fitnessapi.feature.training.tag.service.TagService;
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
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public SliceResponse<TagResponse> getTags(Pageable pageable) {
        return SliceResponse.from(tagService.getTags(pageable));
    }

    @GetMapping("/{slug}")
    public TagResponse getBySlug(@PathVariable @Slug String slug) {
        return tagService.getBySlug(slug);
    }

    @PostMapping
    public ResponseEntity<TagResponse> create(@RequestBody @Valid CreateTagRequest request) {
        TagResponse response = tagService.create(request);

        return ResponseEntity.created(buildLocation(response.slug())).body(response);
    }

    @PatchMapping("/{slug}")
    public TagResponse update(@PathVariable @Slug String slug, @RequestBody @Valid UpdateTagRequest request) {
        return tagService.update(slug, request);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable @Slug String slug) {
        tagService.delete(slug);

        return ResponseEntity.noContent().build();
    }
}
