package io.github.artsobol.fitnessapi.feature.user.web;

import io.github.artsobol.fitnessapi.feature.user.dto.request.CreateProfileRequest;
import io.github.artsobol.fitnessapi.feature.user.dto.request.UpdateProfileRequest;
import io.github.artsobol.fitnessapi.feature.user.dto.response.ProfileResponse;
import io.github.artsobol.fitnessapi.feature.user.service.ProfileService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        ProfileResponse response = service.getProfileByUserId(userPrincipal.userId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getProfileByUsername(@PathVariable String username) {
        ProfileResponse response = service.getProfileByUsername(username);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid CreateProfileRequest request
    ) {
        ProfileResponse response = service.createProfile(userPrincipal.userId(), request);

        return ResponseEntity.created(UriUtils.buildLocation(response.user().username())).body(response);
    }

    @PatchMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid UpdateProfileRequest request
    ) {
        ProfileResponse response = service.updateProfile(userPrincipal.userId(), request);

        return ResponseEntity.ok(response);
    }
}
