package com.example.vickey.service;

import com.example.vickey.LikeKey;
import com.example.vickey.dto.LikedVideosResponse;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Like;
import com.example.vickey.entity.Video;
import com.example.vickey.entity.User;
import com.example.vickey.repository.EpisodeRepository;
import com.example.vickey.repository.LikeRepository;
import com.example.vickey.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, VideoRepository videoRepository, EpisodeRepository episodeRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.videoRepository = videoRepository;
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
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

        // User 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Video 가져오기
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        // LikeKey 생성
        LikeKey key = new LikeKey();
        key.setUserId(userId);
        key.setVideoId(videoId);

        // Like 생성 또는 조회
        Like like = likeRepository.findById(key).orElse(new Like());
        like.setId(key);
        like.setUser(user);    // User 설정
        like.setVideo(video);  // Video 설정

        // Like 저장
        likeRepository.save(like);

        // 에피소드의 좋아요 수 증가
        Episode episode = video.getEpisode();
        System.out.println("episode.getLikeCount()=" + (episode.getLikeCount() + 1) + ": saved");
        episode.setLikeCount(episode.getLikeCount() + 1);
        episodeRepository.save(episode);

    }

    public void unlikeVideo(String userId, Long videoId) {
        LikeKey key = new LikeKey();
        key.setUserId(userId);
        key.setVideoId(videoId);

        // Like 조회 후 삭제
        likeRepository.findById(key).ifPresent(likeRepository::delete);

        // Video 및 Episode에서 좋아요 수 감소
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        Episode episode = video.getEpisode();
        System.out.println("like.getLikeCount() =" + (episode.getLikeCount() -1) + ": deleted");
        episode.setLikeCount(episode.getLikeCount() - 1);
        episodeRepository.save(episode);

    }

    public boolean isLikedByUser(String userId, Long videoId) {
        return likeRepository.existsByUserIdAndVideoId(userId, videoId);
    }

}
