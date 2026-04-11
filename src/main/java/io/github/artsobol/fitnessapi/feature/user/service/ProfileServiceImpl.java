package io.github.artsobol.fitnessapi.feature.user.service;

import io.github.artsobol.fitnessapi.exception.http.ConflictException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.user.dto.request.CreateProfileRequest;
import io.github.artsobol.fitnessapi.feature.user.dto.request.UpdateProfileRequest;
import io.github.artsobol.fitnessapi.feature.user.dto.response.ProfileResponse;
import io.github.artsobol.fitnessapi.feature.user.entity.Profile;
import io.github.artsobol.fitnessapi.feature.user.mapper.ProfileMapper;
import io.github.artsobol.fitnessapi.feature.user.repository.ProfileRepository;
import io.github.artsobol.fitnessapi.feature.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserFinder userFinder;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('USER')")
    public ProfileResponse getProfileByUserId(Long userId) {
        return profileMapper.toResponse(getProfileByUserId(userFinder.findById(userId)));
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('USER')")
    public ProfileResponse getProfileByUsername(String username) {
        log.debug("Find profile username={}", username);
        User user = userFinder.findByUsername(username);
        Profile profile = getProfileByUserId(user);
        return profileMapper.toResponse(profile);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('USER')")
    public ProfileResponse createProfile(Long userId, CreateProfileRequest request) {
        log.info("Create profile userId={}", userId);
        ensureProfileNotExists(userId);

        User user = userFinder.findById(userId);
        Profile entity = Profile.create(user, request.firstName(), request.lastName());

        profileRepository.save(entity);
        return profileMapper.toResponse(entity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('USER')")
    public ProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        log.info("Update profile userId={}", userId);
        Profile entity = getProfileByUserId(userFinder.findById(userId));
        updateProfileInformation(entity, request);
        profileRepository.save(entity);

        return profileMapper.toResponse(entity);
    }

    private Profile getProfileByUserId(User user) {
        log.debug("Find profile userId={}", user.getId());
        return profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("profile.id.not.found", user.getId()));
    }

    private void ensureProfileNotExists(Long userId) {
        log.debug("Check profile doesn't exist userId={}", userId);
        if (profileRepository.existsByUserId(userId)) {
            throw new ConflictException("profile.id.exists", userId);
        }
    }

    private void updateProfileInformation(Profile entity, UpdateProfileRequest request) {
        updateFirstName(entity, request.firstName());
        updateLastName(entity, request.lastName());
    }

    private void updateFirstName(Profile entity, String firstName) {
        if (firstName != null && !firstName.isBlank()) {
            log.debug(
                    "Update firstname from: {} to: {} for user id: {}",
                    entity.getFirstName(),
                    firstName,
                    entity.getId()
            );
            entity.updateFirstName(firstName);
        }
    }

    private void updateLastName(Profile entity, String lastName) {
        if (lastName != null && !lastName.isBlank()) {
            log.debug(
                    "Update lastname from: {} to: {} for user id: {}",
                    entity.getLastName(),
                    lastName,
                    entity.getId()
            );
            entity.updateLastName(lastName);
        }
    }
}
