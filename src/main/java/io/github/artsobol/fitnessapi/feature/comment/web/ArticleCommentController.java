package io.github.artsobol.fitnessapi.feature.comment.web;

import io.github.artsobol.fitnessapi.feature.comment.dto.request.CreateCommentRequest;
import io.github.artsobol.fitnessapi.feature.comment.dto.response.CommentResponse;
import io.github.artsobol.fitnessapi.feature.comment.service.CommentService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/articles/{articleId}/comments")
@RequiredArgsConstructor
public class ArticleCommentController {

    private final CommentService service;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getArticleComments(
            @PathVariable Long articleId
    ) {
        List<CommentResponse> response = service.getArticleComments(articleId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long articleId,
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid CreateCommentRequest request
    ) {
        CommentResponse response = service.createComment(principal.userId(), articleId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
