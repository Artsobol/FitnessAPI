package io.github.artsobol.fitnessapi.feature.article.service;

import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.article.dto.request.CreateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.request.UpdateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.response.ArticleResponse;
import io.github.artsobol.fitnessapi.feature.article.entity.Article;
import io.github.artsobol.fitnessapi.feature.article.mapper.ArticleMapper;
import io.github.artsobol.fitnessapi.feature.article.repository.ArticleRepository;
import io.github.artsobol.fitnessapi.feature.category.entity.Category;
import io.github.artsobol.fitnessapi.feature.category.service.CategoryFinder;
import io.github.artsobol.fitnessapi.feature.video.entity.Video;
import io.github.artsobol.fitnessapi.feature.video.service.VideoFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService, ArticleFinder {

    private final ArticleMapper mapper;
    private final VideoFinder videoFinder;
    private final CategoryFinder categoryFinder;
    private final ArticleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getById(Long id) {
        Article entity = findByIdOrThrow(id);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> getAll() {
        log.debug("Finding all articles");
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ArticleResponse create(CreateArticleRequest request) {
        log.info("Creating article ");
        Article entity = Article.create(request.title(), request.description());
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ArticleResponse update(Long id, UpdateArticleRequest request) {
        log.info("Updating article with id: {}", id);
        Article entity = findByIdOrThrow(id);
        if (request.title() != null) {
            entity.updateTitle(request.title());
        }
        if (request.description() != null) {
            entity.updateDescription(request.description());
        }

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ArticleResponse addVideo(Long articleId, Long videoId) {
        log.info("Add video with id: {} for article with id: {}", videoId, articleId);
        Article entity = findByIdOrThrow(articleId);
        Video video = videoFinder.findByIdOrThrow(videoId);
        entity.addVideo(video);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ArticleResponse addCategory(Long articleId, Long categoryId) {
        log.info("Add category with id: {} for article with id: {}", categoryId, articleId);
        Article entity = findByIdOrThrow(articleId);
        Category category = categoryFinder.findByIdOrThrow(categoryId);
        entity.addCategory(category);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ArticleResponse removeVideo(Long articleId, Long videoId) {
        log.info("Remove video with id: {} for article with id: {}", videoId, articleId);
        Article entity = findByIdOrThrow(articleId);
        Video video = videoFinder.findByIdOrThrow(videoId);
        entity.removeVideo(video);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ArticleResponse removeCategory(Long articleId, Long categoryId) {
        log.info("Delete category with id: {} for article with id: {}", categoryId, articleId);
        Article entity = findByIdOrThrow(articleId);
        Category category = categoryFinder.findByIdOrThrow(categoryId);
        entity.removeCategory(category);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting article with id: {}", id);
        Article entity = findByIdOrThrow(id);
        repository.delete(entity);
    }

    @Override
    public Article findByIdOrThrow(Long id) {
        log.debug("Finding article with id: {}", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("{article.id.not.found}", id));
    }
}
