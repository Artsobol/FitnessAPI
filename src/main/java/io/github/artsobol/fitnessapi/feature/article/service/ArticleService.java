package io.github.artsobol.fitnessapi.feature.article.service;

import io.github.artsobol.fitnessapi.feature.article.dto.request.CreateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.request.UpdateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.response.ArticleResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleService {

    ArticleResponse getById(Long id);

    Slice<ArticleResponse> getAll(Pageable pageable);

    ArticleResponse create(CreateArticleRequest request);

    ArticleResponse update(Long id, UpdateArticleRequest request);

    ArticleResponse addVideo(Long articleId, Long videoId);

    ArticleResponse addCategory(Long articleId, Long categoryId);

    void removeVideo(Long articleId, Long videoId);

    void removeCategory(Long articleId, Long categoryId);

    void delete(Long id);
}
