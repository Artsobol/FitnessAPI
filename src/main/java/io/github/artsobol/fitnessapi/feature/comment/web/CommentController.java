package io.github.artsobol.fitnessapi.feature.comment.web;

import io.github.artsobol.fitnessapi.feature.comment.dto.request.UpdateCommentRequest;
import io.github.artsobol.fitnessapi.feature.comment.dto.response.CommentResponse;
import io.github.artsobol.fitnessapi.feature.comment.service.CommentService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid UpdateCommentRequest request
    ) {
        CommentResponse response = service.updateComment(commentId, principal.userId(), request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponse> delete(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        service.deactivateComment(commentId, principal.userId());

        return ResponseEntity.noContent().build();
    }

}
