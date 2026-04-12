package io.github.artsobol.fitnessapi.feature.training.rating.web;

import io.github.artsobol.fitnessapi.api.common.dto.SliceResponse;
import io.github.artsobol.fitnessapi.feature.training.rating.dto.request.CreateTrainingRatingRequest;
import io.github.artsobol.fitnessapi.feature.training.rating.dto.response.TrainingRatingResponse;
import io.github.artsobol.fitnessapi.feature.training.rating.service.TrainingRatingService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/training/{trainingId}/ratings")
@RequiredArgsConstructor
public class TrainingRatingController {

    private final TrainingRatingService trainingRatingService;

    @PostMapping
    public ResponseEntity<TrainingRatingResponse> create(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable @Positive Long trainingId,
            @RequestBody @Valid CreateTrainingRatingRequest request
    ) {
        TrainingRatingResponse response = trainingRatingService.create(trainingId, userPrincipal.userId(), request);

        return ResponseEntity.created(UriUtils.buildLocation(trainingId)).body(response);
    }

    @GetMapping
    public SliceResponse<TrainingRatingResponse> getAll(@PathVariable @Positive Long trainingId, Pageable pageable
    ) {
        return SliceResponse.from(trainingRatingService.getAll(trainingId, pageable));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable @Positive Long trainingId
    ) {
        trainingRatingService.delete(trainingId, userPrincipal.userId());

        return ResponseEntity.noContent().build();
    }

}
