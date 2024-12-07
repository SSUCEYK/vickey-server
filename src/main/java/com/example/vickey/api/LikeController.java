package com.example.vickey.api;

import com.example.vickey.dto.LikedVideosResponse;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Like;
import com.example.vickey.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/user/{userId}/episodes")
    public List<Episode> getLikedEpisodes(@PathVariable Long userId) {
        return likeService.getLikedEpisodes(userId);
    }


//    @GetMapping("/user/{userId}/episodes/{episodeId}")
//    public List<String> getLikedVideosByEpisode(@PathVariable Long userId, @PathVariable Long episodeId) {
//        List<Like> likes = likeRepository.findAllByUserIdAndVideo_Episode_EpisodeId(userId, episodeId);
//
//        // 비디오 썸네일 URL 리스트 반환
//        return likes.stream()
//                .map(like -> like.getVideo().getThumbnailUrl())
//                .collect(Collectors.toList());
//    }

    @GetMapping("/user/{userId}")
    public List<Like> getUserLikes(@PathVariable Long userId) {
        return likeService.getUserLikes(userId);
    }


    @GetMapping("/user/{userId}/episodes/{episodeId}")
    public ResponseEntity<List<LikedVideosResponse>> getLikedVideosByEpisode(@PathVariable Long userId, @PathVariable Long episodeId) {
        List<LikedVideosResponse> likedVideos = likeService.getLikedVideosByEpisode(userId, episodeId);
        return ResponseEntity.ok(likedVideos);
    }
}
