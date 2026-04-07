package io.github.artsobol.fitnessapi.feature.video.service;

import io.github.artsobol.fitnessapi.feature.video.dto.request.CreateVideoRequest;
import io.github.artsobol.fitnessapi.feature.video.dto.request.UpdateVideoRequest;
import io.github.artsobol.fitnessapi.feature.video.dto.response.VideoResponse;

public interface VideoService {

    VideoResponse getVideoById(Long id);

    VideoResponse createVideo(CreateVideoRequest request);

    VideoResponse updateVideo(Long id, UpdateVideoRequest request);

    void deleteVideo(Long id);
}
