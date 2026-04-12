package io.github.artsobol.fitnessapi.feature.training.favourite.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.training.favourite.dto.response.TrainingFavouriteResponse;
import io.github.artsobol.fitnessapi.feature.training.favourite.service.TrainingFavouriteService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/training/")
@RequiredArgsConstructor
public class TrainingFavouriteController {

    private final TrainingFavouriteService trainingFavouriteService;

    @PostMapping("/{trainingId}")
    public ResponseEntity<TrainingFavouriteResponse> create(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable @Positive Long trainingId
    ) {
        TrainingFavouriteResponse response = trainingFavouriteService.create(userPrincipal.userId(), trainingId);

        return ResponseEntity.created(UriUtils.buildLocation(trainingId)).body(response);
    }

    @GetMapping
    public SliceResponse<TrainingFavouriteResponse> getAll(
            Pageable pageable,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return SliceResponse.from(trainingFavouriteService.getAll(userPrincipal.userId(), pageable));
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable @Positive Long trainingId
    ) {
        trainingFavouriteService.delete(userPrincipal.userId(), trainingId);

        return ResponseEntity.noContent().build();
    }

}
