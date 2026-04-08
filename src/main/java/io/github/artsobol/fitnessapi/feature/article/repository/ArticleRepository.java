package io.github.artsobol.fitnessapi.feature.article.repository;

import io.github.artsobol.fitnessapi.feature.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
}
