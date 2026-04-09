package io.github.artsobol.fitnessapi.feature.comment.repository;

import io.github.artsobol.fitnessapi.feature.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleIdAndIsActiveTrue(Long articleId);

    Optional<Comment> findByIdAndIsActiveTrue(Long id);

    boolean existsByIdAndUserId(Long id, Long userId);
}
