package io.github.artsobol.fitnessapi.feature.article.comment.web;

import io.github.artsobol.fitnessapi.feature.article.comment.dto.request.UpdateCommentRequest;
import io.github.artsobol.fitnessapi.feature.article.comment.dto.response.CommentResponse;
import io.github.artsobol.fitnessapi.feature.article.comment.service.CommentService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PatchMapping("/{commentId}")
    public CommentResponse update(
            @PathVariable @Positive Long commentId,
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid UpdateCommentRequest request
    ) {
        return service.updateComment(commentId, principal.userId(), request);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable @Positive Long commentId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        service.deactivateComment(commentId, principal.userId());

        return ResponseEntity.noContent().build();
    }

}
