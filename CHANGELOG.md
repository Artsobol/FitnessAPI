# Changelog

## [0.4.0]

### Added

- `tag`, `type`, and `training` features;
- `TagController`, `TagService`, `TagRepository`, and `TagMapper`;
- `TypeController`, `TypeService`, `TypeRepository`, and `TypeMapper`;
- `TrainingController`, `TrainingService`, `TrainingRepository`, `TrainingExerciseRepository`, and training mappers;
- DTOs for tag, type, and training create, update, and read operations;
- `Tag`, `Type`, `Training`, and `TrainingExercise` entities;
- support for training exercise items with duplicate exercises inside one training;
- training item deletion by `trainingExerciseId` via `/trainings/{trainingId}/exercise-items/{trainingExerciseId}`;
- Liquibase migrations for `tag`, `type`, `training`, `training_tag`, `training_type`, and `training_exercise` tables;
- localized validation and error messages for comment, exercise, tag, type, training, and training exercise flows.

### Changed

- README updated to reflect the current project scope and launch steps;
- project documentation aligned with the training module and new taxonomy features;
- i18n bundles completed for all keys currently used in the codebase;
- project version bumped to `0.4.0`.

## [0.3.0]

### Added

- article and category features;
- `ArticleController`, `ArticleService`, `ArticleRepository`, and `ArticleMapper`;
- `CategoryController`, `CategoryService`, `CategoryRepository`, and `CategoryMapper`;
- DTOs for article and category create, update, and read operations;
- `Article` and `Category` entities;
- Liquibase migrations for `article`, `category`, `article_category`, and `article_video` tables;
- mappers for article and category DTO conversion.

### Changed

- Liquibase master changelog extended with `v0.3.0`;
- project version bumped to `0.3.0`.

## [0.2.0]

### Added

- added the `video` module;
- implemented `VideoController`, `VideoService`, `VideoRepository`, and `VideoMapper`;
- added DTOs for video create, update, and read operations;
- added the `Video` entity;
- added a Liquibase migration for the `video` table;
- integrated `MapStruct` for entity-to-DTO mapping.

### Changed

- extended the Liquibase master changelog with version `v0.2.0`;
- bumped the project version to `0.2.0`.

## [0.1.0]

### Added

- created the initial Spring Boot backend for FitnessAPI;
- implemented user registration;
- implemented user login;
- implemented refresh token rotation;
- added the `User` model with roles and active flag;
- added the `RefreshToken` model with session, IP address, `User-Agent`, device, and revoke metadata;
- implemented authentication services, access token issuing, and refresh token issuing;
- configured JWT-based security;
- added Liquibase migrations for `users` and `refresh_token`;
- added validation, localized messages, and centralized error handling;
- integrated YAUAA for device detection from `User-Agent`.
