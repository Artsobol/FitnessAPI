package io.github.artsobol.fitnessapi.feature.training.web;

import io.github.artsobol.fitnessapi.feature.training.dto.response.TrainingFavouriteResponse;
import io.github.artsobol.fitnessapi.feature.training.service.TrainingFavouriteService;
import io.github.artsobol.fitnessapi.security.user.UserPrincipal;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/training/")
@RequiredArgsConstructor
public class TrainingFavouriteController {

    private final TrainingFavouriteService trainingFavouriteService;

    @PostMapping("/{trainingId}")
    public ResponseEntity<TrainingFavouriteResponse> create(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long trainingId
    ) {
        TrainingFavouriteResponse response = trainingFavouriteService.create(userPrincipal.userId(), trainingId);

        return ResponseEntity.created(UriUtils.buildLocation(trainingId)).body(response);
    }

    @GetMapping
    public List<TrainingFavouriteResponse> getAll(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return trainingFavouriteService.getAll(userPrincipal.userId());
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long trainingId
    ) {
        trainingFavouriteService.delete(userPrincipal.userId(), trainingId);

        return ResponseEntity.noContent().build();
    }

}
