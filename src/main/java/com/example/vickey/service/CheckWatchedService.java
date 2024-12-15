package com.example.vickey.service;

import com.example.vickey.dto.CheckWatchedResponse;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.User;
import com.example.vickey.entity.Video;
import com.example.vickey.repository.CheckWatchedRepository;
import com.example.vickey.repository.EpisodeRepository;
import com.example.vickey.repository.UserRepository;
import com.example.vickey.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class CheckWatchedService {

    private final EpisodeService episodeService;
    private final CheckWatchedRepository checkWatchedRepository;
    private final VideoRepository videoRepository;
    private final EpisodeRepository episodeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckWatchedService(CheckWatchedRepository checkWatchedRepository, EpisodeService episodeService, VideoRepository videoRepository, EpisodeRepository episodeRepository, UserRepository userRepository) {
        this.checkWatchedRepository = checkWatchedRepository;
        this.episodeService = episodeService;
        this.videoRepository = videoRepository;
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
    }

//    public List<CheckWatchedResponse> getUserHistory(String userId) {
//        List<CheckWatched> histories = checkWatchedRepository.findAllByUserId(userId);
//
//        return histories.stream().map(history -> {
//
//            CheckWatchedResponse response = new CheckWatchedResponse(history);
//
//            Long episodeId = history.getVideo().getEpisodeId();
//            response.setEpisode(episodeService.getEpisodeById(episodeId)); // VideoId로 Episode 조회 후 Set
//
//            return response;
//
//        }).collect(Collectors.toList());
//    }

    @Cacheable(value = "userHistory", key = "#userId")
    public List<CheckWatchedResponse> getUserHistory(String userId) {
        Pageable pageable = PageRequest.of(0, 18);
        return checkWatchedRepository.findTopByUserId(userId, pageable);
    }

    @Transactional
    public void markAsWatched(String userId, Long videoId) {

        // 1. User와 Video를 데이터베이스에서 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found: " + videoId));

        System.out.println("User: " + user.getUserId() + ", " + user.getEmail() + ", " + user.getUsername());
        System.out.println("Video: " + video.getVideoId() + ", " + video.getVideoNum() + ", " + video.getVideoUrl());

        // 2. CheckWatched 테이블에 삽입 또는 업데이트
        checkWatchedRepository.upsertWatched(userId, videoId, -1);

        // 3. 에피소드 조회수 증가
        Episode episode = video.getEpisode();
        if (episode != null) {
            episode.setWatchCount(episode.getWatchCount() + 1);
            episodeRepository.save(episode);
        }
    }


}
