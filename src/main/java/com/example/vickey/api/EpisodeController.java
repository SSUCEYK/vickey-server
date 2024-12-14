package com.example.vickey.api;
import com.example.vickey.dto.EpisodeDTO;
import com.example.vickey.service.EpisodeService;

import com.example.vickey.entity.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<EpisodeDTO>> getAllEpisodes() {

        List<Episode> episodes = episodeService.getAllEpisodes();

        // episodes가 null일 경우 빈 리스트 반환
        if (episodes == null || episodes.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<EpisodeDTO> episodeDTOs = episodes.stream()
                .map(EpisodeDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(episodeDTOs);

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
    public ResponseEntity<EpisodeDTO> getEpisodeById(@PathVariable("id") Long id) {
        Optional<Episode> episode = episodeService.getEpisodeById(id);

        if (episode == null) {
            return ResponseEntity.notFound().build();
        }

        EpisodeDTO episodeDTO = new EpisodeDTO(episode.get());

        return ResponseEntity.ok(episodeDTO);
    }

    @GetMapping("/randomEpisodeIds")
    public List<Long> getRandomEpisodeIds(@RequestParam("n") int n) {
        return episodeService.getRandomEpisodeIds(n);
    }


    // randomEpisodes?n=4 -> 랜덤 에피소드 4개 리턴한다는 뜻
    @GetMapping("/randomEpisodes")
    public ResponseEntity<List<EpisodeDTO>> getRandomEpisodes(@RequestParam int n) {
        List<Episode> episodes = episodeService.getRandomEpisodes(n);

        // episodes가 null일 경우 빈 리스트 반환
        if (episodes == null || episodes.isEmpty()) {
            System.out.println("getRandomEpisodes: return emptyList");
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<EpisodeDTO> episodeDTOs = episodes.stream()
                .map(EpisodeDTO::new)
                .collect(Collectors.toList());

        System.out.println("episodeDTOs.size()=" + episodeDTOs.size());
        System.out.println("episodeDTOs.get(0).getEpisodeId(): " + episodeDTOs.get(0).getEpisodeId());

        return ResponseEntity.ok(episodeDTOs);
    }

    // chooseEpisode?n=4 -> 좋아요 가장 많은 4개 에피소드 리턴한다는 뜻
    @GetMapping("/topLikedEpisodes")
    public ResponseEntity<List<EpisodeDTO>> getTopNLikedEpisode(@RequestParam("n") int n) {
        List<Episode> episodes = episodeService.getTopNEpisodesByLikedCount(n);

        // episodes가 null일 경우 빈 리스트 반환
        if (episodes == null || episodes.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<EpisodeDTO> episodeDTOs = episodes.stream()
                .map(EpisodeDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(episodeDTOs);
    }

    // /watchEpisode?n=4 -> 조회수 가장 많은 4개 에피소드 id 리턴한다는 뜻
    @GetMapping("/topWatchedEpisodes")
    public ResponseEntity<List<EpisodeDTO>> getTopNWatchedEpisode(@RequestParam("n") int n) {
        List<Episode> episodes = episodeService.getTopNEpisodesByWatchedCount(n);

        // episodes가 null일 경우 빈 리스트 반환
        if (episodes == null || episodes.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<EpisodeDTO> episodeDTOs = episodes.stream()
                .map(EpisodeDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(episodeDTOs);
    }

    @GetMapping({"/search"})
    public ResponseEntity<List<EpisodeDTO>> searchEpisodes(@RequestParam String searchQuery) {
        List<Episode> episodes = episodeService.searchEpisodes(searchQuery);


        // episodes가 null일 경우 빈 리스트 반환
        if (episodes == null || episodes.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<EpisodeDTO> episodeDTOs = episodes.stream()
                .map(EpisodeDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(episodeDTOs);
    }

    @GetMapping({"/contentInfo"})
    public ResponseEntity<EpisodeDTO> contentInfoEpisode(@RequestParam Long contentInfoQuery) {
        Episode episode = episodeService.contentInfoEpisode(contentInfoQuery);

        if (episode == null) {
            return ResponseEntity.notFound().build();
        }

        EpisodeDTO episodeDTO = new EpisodeDTO(episode);
        return ResponseEntity.ok(episodeDTO);
    }
}

