package io.github.artsobol.fitnessapi.feature.article.article.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.article.article.dto.request.CreateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.article.dto.request.UpdateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.article.dto.response.ArticleResponse;
import io.github.artsobol.fitnessapi.feature.article.article.service.ArticleService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    public ArticleResponse getById(@PathVariable @Positive Long articleId) {
        return articleService.getById(articleId);
    }

    @GetMapping
    public SliceResponse<ArticleResponse> getAll(Pageable pageable) {
        return SliceResponse.from(articleService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> create(
            @RequestBody @Valid CreateArticleRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        ArticleResponse response = articleService.create(request, userPrincipal.userId());

        return ResponseEntity.created(UriUtils.buildLocation(response.id())).body(response);
    }

    @PatchMapping("/{articleId}")
    public ArticleResponse update(
            @PathVariable @Positive Long articleId,
            @RequestBody @Valid UpdateArticleRequest request

    ) {
        return articleService.update(request, articleId);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long articleId

    ) {
        articleService.delete(articleId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{articleId}/categories/{categoryId}")
    public ArticleResponse addCategory(
            @PathVariable @Positive Long articleId,
            @PathVariable @Positive Long categoryId

    ) {
        return articleService.addCategory(articleId, categoryId);
    }

    @DeleteMapping("/{articleId}/categories/{categoryId}")
    public ResponseEntity<Void> removeCategory(
            @PathVariable @Positive Long articleId,
            @PathVariable @Positive Long categoryId

    ) {
        articleService.removeCategory(articleId, categoryId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{articleId}/videos/{videoId}")
    public ArticleResponse addVideo(@PathVariable @Positive Long articleId, @PathVariable @Positive Long videoId

    ) {
        return articleService.addVideo(articleId, videoId);
    }

    @DeleteMapping("/{articleId}/videos/{videoId}")
    public ResponseEntity<Void> removeVideo(@PathVariable @Positive Long articleId, @PathVariable @Positive Long videoId

    ) {
        articleService.removeVideo(articleId, videoId);

        return ResponseEntity.noContent().build();
    }

}
