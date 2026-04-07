package io.github.artsobol.fitnessapi.feature.video.service;

import io.github.artsobol.fitnessapi.exception.http.ConflictException;
import io.github.artsobol.fitnessapi.exception.http.NotFoundException;
import io.github.artsobol.fitnessapi.feature.video.dto.request.CreateVideoRequest;
import io.github.artsobol.fitnessapi.feature.video.dto.request.UpdateVideoRequest;
import io.github.artsobol.fitnessapi.feature.video.dto.response.VideoResponse;
import io.github.artsobol.fitnessapi.feature.video.entity.Video;
import io.github.artsobol.fitnessapi.feature.video.mapper.VideoMapper;
import io.github.artsobol.fitnessapi.feature.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository repository;
    private final VideoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public VideoResponse getVideoById(Long id) {
        Video entity = getVideo(id);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public VideoResponse createVideo(CreateVideoRequest request) {
        log.info("Creating video");
        Video entity = Video.create(request.url(), request.title());
        repository.save(entity);
        log.info("Video was creating with id: {}", entity.getId());

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public VideoResponse updateVideo(Long id, UpdateVideoRequest request) {
        log.info("Updating video with id: {}", id);
        Video entity = getVideo(id);
        if (request.url() != null && !request.url().equals(entity.getUrl())) {
            ensureUrlNotExists(request.url());
            log.debug("Video with id: {} change url from: {} - to: {}", id, entity.getUrl(), request.url());
            entity.changeUrl(request.url());
        }
        if (request.title() != null) {
            entity.changeTitle(request.title());
            log.debug("Video with id: {} change title from: {} - to: {}", id, entity.getTitle(), request.title());
        }
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        log.info("Deleting video with id: {}", id);
        Video entity = getVideo(id);
        repository.delete(entity);
    }

    private Video getVideo(Long id) {
        log.debug("Finding video with id: {}", id);
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("{video.not.found}", id)
        );
    }

    private void ensureUrlNotExists(String url) {
        if (repository.existsByUrl(url)) {
            throw new ConflictException("{video.url.exists}", url);
        }
    }
}
