package io.github.artsobol.fitnessapi.feature.category.repository;

import io.github.artsobol.fitnessapi.feature.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
