package io.github.artsobol.fitnessapi.feature.video.web;

import io.github.artsobol.fitnessapi.feature.video.dto.request.CreateVideoRequest;
import io.github.artsobol.fitnessapi.feature.video.dto.request.UpdateVideoRequest;
import io.github.artsobol.fitnessapi.feature.video.dto.response.VideoResponse;
import io.github.artsobol.fitnessapi.feature.video.service.VideoService;
import io.github.artsobol.fitnessapi.utils.UriUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService service;

    @GetMapping("/{videoId}")
    public VideoResponse get(@PathVariable @Positive Long videoId) {
        return service.getVideoById(videoId);
    }


    @PostMapping
    public ResponseEntity<VideoResponse> create(@RequestBody @Valid CreateVideoRequest request) {
        VideoResponse response = service.createVideo(request);

        return ResponseEntity.created(UriUtils.buildLocation(response.id())).body(response);
    }


    @PatchMapping("/{videoId}")
    public VideoResponse update(@PathVariable @Positive Long videoId, @RequestBody @Valid UpdateVideoRequest request) {
        return service.updateVideo(videoId, request);
    }


    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long videoId) {
        service.deleteVideo(videoId);

        return ResponseEntity.noContent().build();
    }
}
