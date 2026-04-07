# Changelog

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
