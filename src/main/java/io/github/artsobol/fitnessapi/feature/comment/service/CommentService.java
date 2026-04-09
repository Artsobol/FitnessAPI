package io.github.artsobol.fitnessapi.feature.comment.service;

import io.github.artsobol.fitnessapi.feature.comment.dto.request.CreateCommentRequest;
import io.github.artsobol.fitnessapi.feature.comment.dto.request.UpdateCommentRequest;
import io.github.artsobol.fitnessapi.feature.comment.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long userId, Long articleId, CreateCommentRequest request);

    CommentResponse updateComment(Long commentId, Long userId, UpdateCommentRequest request);

    List<CommentResponse> getArticleComments(Long articleId);

    void deactivateComment(Long commentId, Long userId);
}
