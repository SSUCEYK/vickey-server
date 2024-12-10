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
import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/user/{userId}/episodes")
    public ResponseEntity<List<Episode>> getLikedEpisodes(@PathVariable String userId) {
        List<Episode> likedEpisodes = likeService.getLikedEpisodes(userId);

        if (likedEpisodes.isEmpty()) {
            // likedEpisodes가 없는 경우 204 No Content 반환
            return ResponseEntity.noContent().build();
        }

        // likedEpisodes가 있는 경우 200 OK와 데이터 반환
        return ResponseEntity.ok(likedEpisodes);
    }

    @GetMapping("/user/{userId}/episodes/{episodeId}")
    public ResponseEntity<List<LikedVideosResponse>> getLikedVideosByEpisode(@PathVariable String userId, @PathVariable Long episodeId) {
        List<LikedVideosResponse> likedVideos = likeService.getLikedVideosByEpisode(userId, episodeId);
        return ResponseEntity.ok(likedVideos);
    }

    @GetMapping("/user/{userId}")
    public List<Like> getUserLikes(@PathVariable String userId) {
        return likeService.getUserLikes(userId);
    }

}
