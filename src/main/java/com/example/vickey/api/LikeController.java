package com.example.vickey.api;

import com.example.vickey.dto.EpisodeDTO;
import com.example.vickey.dto.LikedVideosResponse;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Like;
import com.example.vickey.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/user/{userId}/episodes")
    public ResponseEntity<List<EpisodeDTO>> getLikedEpisodes(@PathVariable String userId) {
        List<Episode> likedEpisodes = likeService.getLikedEpisodes(userId);

        if (likedEpisodes==null || likedEpisodes.isEmpty()) {
            // likedEpisodes가 없는 경우 204 No Content 반환
            System.out.println("likedEpisodes is Empty");
            return ResponseEntity.noContent().build();
        }

        List<EpisodeDTO> episodeDTOs = likedEpisodes.stream()
                .map(EpisodeDTO::new)
                .collect(Collectors.toList());

        System.out.println("episodeDTOs.size()=" + episodeDTOs.size());
        System.out.println("episodeDTOs.get(0).getEpisodeId(): " + episodeDTOs.get(0).getEpisodeId());

        return ResponseEntity.ok(episodeDTOs);
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

    @PostMapping("/user/{userId}/videos/{videoId}/like")
    public ResponseEntity<Void> likeVideo(@PathVariable String userId, @PathVariable Long videoId) {
        likeService.likeVideo(userId, videoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{userId}/videos/{videoId}/like")
    public ResponseEntity<Void> unlikeVideo(@PathVariable String userId, @PathVariable Long videoId) {
        likeService.unlikeVideo(userId, videoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/videos/{videoId}/like-status")
    public ResponseEntity<Boolean> isLikedByUser(@PathVariable Long videoId, @RequestParam String userId) {
        try {
            boolean isLiked = likeService.isLikedByUser(userId, videoId);
            return ResponseEntity.ok(isLiked);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
