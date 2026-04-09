package io.github.artsobol.fitnessapi.feature.comment.service;

import io.github.artsobol.fitnessapi.exception.http.ForbiddenException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.article.entity.Article;
import io.github.artsobol.fitnessapi.feature.article.service.ArticleFinder;
import io.github.artsobol.fitnessapi.feature.comment.dto.request.CreateCommentRequest;
import io.github.artsobol.fitnessapi.feature.comment.dto.request.UpdateCommentRequest;
import io.github.artsobol.fitnessapi.feature.comment.dto.response.CommentResponse;
import io.github.artsobol.fitnessapi.feature.comment.entity.Comment;
import io.github.artsobol.fitnessapi.feature.comment.mapper.CommentMapper;
import io.github.artsobol.fitnessapi.feature.comment.repository.CommentRepository;
import io.github.artsobol.fitnessapi.feature.user.entity.User;
import io.github.artsobol.fitnessapi.feature.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final UserFinder userFinder;
    private final ArticleFinder articleFinder;

    @Override
    @Transactional
    public CommentResponse createComment(Long userId, Long articleId, CreateCommentRequest request) {
        log.info("Creating comment for article with id: {} and user id: {}", userId, articleId);
        User user = userFinder.findById(userId);
        Article article = articleFinder.findByIdOrThrow(articleId);

        Comment entity = Comment.create(user, article, request.comment());
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long commentId, Long userId, UpdateCommentRequest request) {
        Comment entity = getById(commentId);
        ensureIsOwner(commentId, userId);
        entity.updateComment(request.comment());

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getArticleComments(Long articleId) {
        log.debug("Finding comment for article with id: {}", articleId);
        return repository.findByArticleIdAndIsActiveTrue(articleId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deactivateComment(Long commentId, Long userId) {
        ensureIsOwner(commentId, userId);
        Comment entity = getById(commentId);
        entity.deactivate();
    }

    private Comment getById(Long id) {
        log.debug("Finding comment with id: {}", id);
        return repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("{comment.id.not.found}", id));
    }

    private void ensureIsOwner(Long commentId, Long userId) {
        if (!repository.existsByIdAndUserId(commentId, userId)) {
            throw new ForbiddenException("{comment.wrong.owner.update}", commentId, userId);
        }
    }
}
