package io.github.artsobol.fitnessapi.feature.video.web;

import io.github.artsobol.fitnessapi.feature.video.dto.request.CreateVideoRequest;
import io.github.artsobol.fitnessapi.feature.video.dto.request.UpdateVideoRequest;
import io.github.artsobol.fitnessapi.feature.video.dto.response.VideoResponse;
import io.github.artsobol.fitnessapi.feature.video.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService service;

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponse> get(@PathVariable Long videoId) {
        VideoResponse response = service.getVideoById(videoId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping
    public ResponseEntity<VideoResponse> create(@RequestBody @Valid CreateVideoRequest request) {
        VideoResponse response = service.createVideo(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PatchMapping("/{videoId}")
    public ResponseEntity<VideoResponse> update(@PathVariable Long videoId, @RequestBody @Valid UpdateVideoRequest request) {
        VideoResponse response = service.updateVideo(videoId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> delete(@PathVariable Long videoId) {
        service.deleteVideo(videoId);

        return ResponseEntity.noContent().build();
    }
}
