package io.github.artsobol.fitnessapi.feature.article.comment.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.article.comment.dto.request.CreateCommentRequest;
import io.github.artsobol.fitnessapi.feature.article.comment.dto.response.CommentResponse;
import io.github.artsobol.fitnessapi.feature.article.comment.service.CommentService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/articles/{articleId}/comments")
@RequiredArgsConstructor
public class ArticleCommentController {

    private final CommentService service;

    @GetMapping
    public SliceResponse<CommentResponse> getArticleComments(@PathVariable @Positive Long articleId, Pageable pageable
    ) {
        return SliceResponse.from(service.getArticleComments(articleId, pageable));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long articleId,
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid CreateCommentRequest request
    ) {
        CommentResponse response = service.createComment(principal.userId(), articleId, request);

        return ResponseEntity.created(UriUtils.buildLocation(response.id())).body(response);
    }
}
