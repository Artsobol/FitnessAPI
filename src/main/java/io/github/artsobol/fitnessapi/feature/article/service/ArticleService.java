package io.github.artsobol.fitnessapi.feature.article.service;

import io.github.artsobol.fitnessapi.feature.article.dto.request.CreateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.request.UpdateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.response.ArticleResponse;

import java.util.List;

public interface ArticleService {

    ArticleResponse getById(Long id);

    List<ArticleResponse> getAll();

    ArticleResponse create(CreateArticleRequest request);

    ArticleResponse update(Long id, UpdateArticleRequest request);

    ArticleResponse addVideo(Long articleId, Long videoId);

    ArticleResponse addCategory(Long articleId, Long categoryId);

    ArticleResponse removeVideo(Long articleId, Long videoId);

    ArticleResponse removeCategory(Long articleId, Long categoryId);

    void delete(Long id);
}
