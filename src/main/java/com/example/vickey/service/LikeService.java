package com.example.vickey.service;

import com.example.vickey.LikeKey;
import com.example.vickey.dto.LikedVideosResponse;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Like;
import com.example.vickey.entity.Video;
import com.example.vickey.repository.EpisodeRepository;
import com.example.vickey.repository.LikeRepository;
import com.example.vickey.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final VideoRepository videoRepository;
    private final EpisodeRepository episodeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, VideoRepository videoRepository, EpisodeRepository episodeRepository) {
        this.likeRepository = likeRepository;
        this.videoRepository = videoRepository;
        this.episodeRepository = episodeRepository;
    }

    public List<LikedVideosResponse> getLikedVideosByEpisode(String userId, Long episodeId) {
        return likeRepository.findLikedVideoInfoByUserIdAndEpisodeId(userId, episodeId);
    }

    public List<Episode> getLikedEpisodes(String userId) {
        List<Like> likes = likeRepository.findAllByUserId(userId);

        // 좋아요가 없으면 빈 리스트 반환
        if (likes.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        // Like에서 Episode 리스트 생성
        return likes.stream()
                .map(like -> like.getVideo().getEpisode()) // Like에서 Episode 추출
                .collect(Collectors.toList());
    }

    public List<Like> getUserLikes(String userId) {
        return likeRepository.findAllByUserId(userId);
    }


    public void likeVideo(String userId, Long videoId) {
        LikeKey key = new LikeKey();
        key.setUserId(userId);
        key.setVideoId(videoId);

        Like like = likeRepository.findById(key).orElse(new Like());
        like.setId(key);
        likeRepository.save(like);

        // 에피소드 좋아요 수 증가
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        Episode episode = video.getEpisode();
        System.out.println("episode.getLikeCount++: " + episode.getLikeCount() + 1);
        episode.setLikeCount(episode.getLikeCount() + 1);
        episodeRepository.save(episode);
    }

    public void unlikeVideo(String userId, Long videoId) {
        Optional<Like> like = likeRepository.findByUserIdAndVideoId(userId, videoId);
        System.out.println("like.isPresent()=" + like.isPresent() + ": deleted");
        like.ifPresent(likeRepository::delete);
    }

}
