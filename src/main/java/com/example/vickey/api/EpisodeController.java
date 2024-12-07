package com.example.vickey.api;

import com.example.vickey.service.EpisodeService;
import com.example.vickey.entity.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/thumbnails")
    public List<String> getEpisodeThumbnails(@RequestBody List<Long> ids) {
        return episodeService.getEpisodeThumbnails(ids);
    }

    @PostMapping("/upload")
    public Episode addEpisode(@RequestBody Episode episode) {
        return episodeService.addEpisode(episode);
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

}
