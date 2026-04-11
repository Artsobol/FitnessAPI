package io.github.artsobol.fitnessapi.feature.article.service;

import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.article.dto.request.CreateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.request.UpdateArticleRequest;
import io.github.artsobol.fitnessapi.feature.article.dto.response.ArticleResponse;
import io.github.artsobol.fitnessapi.feature.article.entity.Article;
import io.github.artsobol.fitnessapi.feature.article.entity.Category;
import io.github.artsobol.fitnessapi.feature.article.mapper.ArticleMapper;
import io.github.artsobol.fitnessapi.feature.article.repository.ArticleRepository;
import io.github.artsobol.fitnessapi.feature.video.entity.Video;
import io.github.artsobol.fitnessapi.feature.video.service.VideoFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Slice<ArticleResponse> getAll(Pageable pageable) {
        log.debug("Fetching articles page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public ArticleResponse create(CreateArticleRequest request) {
        log.info("Creating article title={}", request.title());
        Article entity = Article.create(request.title(), request.description());
        repository.save(entity);

        log.info("Created article id={}", entity.getId());
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ArticleResponse update(Long id, UpdateArticleRequest request) {
        log.info("Updating article id={}", id);
        Article entity = findByIdOrThrow(id);
        entity.applyPatch(request.title(), request.description());

        log.info("Article updated id={}", id);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ArticleResponse addVideo(Long articleId, Long videoId) {
        log.info("Adding video videoId={} articleId={}", videoId, articleId);
        Article entity = findByIdOrThrow(articleId);
        Video video = videoFinder.findByIdOrThrow(videoId);
        entity.addVideo(video);

        log.info("Video added videoId={} articleId={}", videoId, articleId);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ArticleResponse addCategory(Long articleId, Long categoryId) {
        log.info("Adding category categoryId={} articleId={}", categoryId, articleId);
        Article entity = findByIdOrThrow(articleId);
        Category category = categoryFinder.findByIdOrThrow(categoryId);
        entity.addCategory(category);

        log.info("Category added categoryId={} articleId={}", categoryId, articleId);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void removeVideo(Long articleId, Long videoId) {
        log.info("Removing video videoId={} articleId={}", videoId, articleId);
        Article entity = findByIdOrThrow(articleId);
        Video video = videoFinder.findByIdOrThrow(videoId);
        entity.removeVideo(video);
        log.info("Video removed videoId={} articleId={}", videoId, articleId);
    }

    @Override
    @Transactional
    public void removeCategory(Long articleId, Long categoryId) {
        log.info("Removing category categoryId={} articleId={}", categoryId, articleId);
        Article entity = findByIdOrThrow(articleId);
        Category category = categoryFinder.findByIdOrThrow(categoryId);
        entity.removeCategory(category);
        log.info("Category removed categoryId={} articleId={}", categoryId, articleId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting article id={}", id);
        Article entity = findByIdOrThrow(id);
        repository.delete(entity);
        log.info("Article deleted id={}", id);
    }

    @Override
    public Article findByIdOrThrow(Long id) {
        log.debug("Fetching article id={}", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("{article.id.not.found}", id));
    }
}
