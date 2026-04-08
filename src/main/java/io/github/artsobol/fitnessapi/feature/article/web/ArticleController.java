package io.github.artsobol.fitnessapi.feature.article.web;

import io.github.artsobol.fitnessapi.feature.article.dto.request.CreateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.request.UpdateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.response.ArticleResponse;
import io.github.artsobol.fitnessapi.feature.article.service.ArticleService;
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
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService service;

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable Long articleId) {
        ArticleResponse response = service.getById(articleId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAll() {
        List<ArticleResponse> response = service.getAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> create(@RequestBody @Valid CreateArticleRequest request) {
        ArticleResponse response = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> update(
            @PathVariable Long articleId,
            @RequestBody @Valid UpdateArticleRequest request
    ) {
        ArticleResponse response = service.update(articleId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> delete(
            @PathVariable Long articleId
    ) {
        service.delete(articleId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{articleId}/categories/{categoryId}")
    public ResponseEntity<ArticleResponse> addCategory(@PathVariable Long articleId, @PathVariable Long categoryId) {
        ArticleResponse response = service.addCategory(articleId, categoryId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{articleId}/categories/{categoryId}")
    public ResponseEntity<ArticleResponse> removeCategory(@PathVariable Long articleId, @PathVariable Long categoryId) {
        service.removeCategory(articleId, categoryId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{articleId}/videos/{videoId}")
    public ResponseEntity<ArticleResponse> addVideo(@PathVariable Long articleId, @PathVariable Long videoId) {
        ArticleResponse response = service.addVideo(articleId, videoId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{articleId}/videos/{videoId}")
    public ResponseEntity<ArticleResponse> removeVideo(@PathVariable Long articleId, @PathVariable Long videoId) {
        service.removeVideo(articleId, videoId);

        return ResponseEntity.noContent().build();
    }

}
