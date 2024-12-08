package com.example.vickey.api;
import com.example.vickey.service.EpisodeService;

import com.example.vickey.entity.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// 서비스 계층에서 제공하는 메소드를 호출 (웹 요청 응답, 처리)
@RestController
@RequestMapping("/api/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping
    public List<Episode> getAllEpisodes() {
        return episodeService.getAllEpisodes();
    }

    // 썸네일 업로드 : 바로 S3에 이미지 파일 업로드
    @PostMapping("/upload/thumbnail")
    public ResponseEntity<Map<String, String>> uploadThumbnail(@RequestParam("file") MultipartFile file) {
        try{
            String fileUrl = episodeService.uploadThumbnail(file);
            System.out.println("uploadThumbnail/ fileUrl: " + fileUrl);
            return ResponseEntity.ok(Map.of("url", fileUrl));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload thumbnail"));
        }
    }

    @PostMapping("/thumbnails")
    public List<String> getEpisodeThumbnails(@RequestBody List<Long> ids) {
        return episodeService.getEpisodeThumbnails(ids);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> addEpisode(@RequestBody Episode episode) {
        Episode savedEpisode = episodeService.addEpisode(episode);
        System.out.println("Saved Episode Data: " + episode); // 요청 데이터 확인
        System.out.println("EpisodeController / savedEpisode.thumbnailUrl:" + episode.getThumbnailUrl());

        return ResponseEntity.ok(Map.of(
                "id", savedEpisode.getEpisodeId(),
                "message", "Episode created successfully"
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Episode> getEpisodeById(@PathVariable("id") Long id) {
        Optional<Episode> episode = episodeService.getEpisodeById(id);
        return episode.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/randomEpisodeIds")
    public List<Long> getRandomEpisodeIds(@RequestParam("n") int n) {
        return episodeService.getRandomEpisodeIds(n);
    }

    @GetMapping("/randomEpisodes")
    public ResponseEntity<List<Episode>> getRandomEpisodes(@RequestParam int n) {
        List<Episode> randomEpisodes = episodeService.getRandomEpisodes(n);
        return ResponseEntity.ok(randomEpisodes);
    }

    @GetMapping({"/search"})
    public List<Episode> searchEpisodes(@RequestParam String searchQuery) {
        return episodeService.searchEpisodes(searchQuery);
    }

    @GetMapping({"/contentInfo"})
    public Episode contentInfoEpisodes(@RequestParam Integer contentInfoQuery) {
        return episodeService.contentInfoEpisodes(contentInfoQuery);
    }
}

