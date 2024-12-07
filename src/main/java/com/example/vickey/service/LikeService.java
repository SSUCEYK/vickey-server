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

    public List<LikedVideosResponse> getLikedVideosByEpisode(Long userId, Long episodeId) {
        return likeRepository.findLikedVideoInfoByUserIdAndEpisodeId(userId, episodeId);
    }

    public List<Episode> getLikedEpisodes(Long userId) {
        List<Like> likes = likeRepository.findAllByUserId(userId);

        return likes.stream()
                .map(like -> like.getVideo().getEpisode()) // Like에서 Episode 추출
                .collect(Collectors.toList());
    }

    public List<Like> getUserLikes(Long userId) {
        return likeRepository.findAllByUserId(userId);
    }

}
