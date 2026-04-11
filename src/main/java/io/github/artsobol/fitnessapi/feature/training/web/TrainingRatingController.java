package io.github.artsobol.fitnessapi.feature.training.web;

import io.github.artsobol.fitnessapi.feature.training.dto.request.CreateTrainingRatingRequest;
import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingRatingResponse;
import io.github.artsobol.fitnessapi.feature.training.service.TrainingRatingService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

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
    public List<TrainingRatingResponse> getAll(
            @PathVariable @Positive Long trainingId
    ) {
        return trainingRatingService.getTrainingRatings(trainingId);
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
