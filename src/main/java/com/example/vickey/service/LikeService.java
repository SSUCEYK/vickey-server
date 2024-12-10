package com.example.vickey.service;

import com.example.vickey.dto.LikedVideosResponse;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Like;
import com.example.vickey.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
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

}
