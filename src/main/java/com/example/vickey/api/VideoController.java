package com.example.vickey.api;

import com.example.vickey.service.VideoService;
import com.example.vickey.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "http://localhost:8080")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // 비디오 업로드 : 바로 S3에 동영상을 업로드
    @PostMapping("/upload/{episodeId}")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @PathVariable("episodeId") Long episodeId) throws IOException {

        try{
            //1. S3에 업로드
            Map<String, Object> result = videoService.uploadVideo(file);
            String videoUrl = (String) result.get("url");
            long duration = (Long) result.get("duration");

            // 2. DB에 저장
            System.out.println("Calling saveVideo...");
            videoService.saveVideo(episodeId, videoUrl, duration);

            return ResponseEntity.ok("Video uploaded successfully: " + videoUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload video");
        }
    }

    @GetMapping
    public ResponseEntity<List<Video>> getVideos(@RequestParam(required = false) Long episodeId) {
        if (episodeId == null) {
            // episodeId 없으면 전체 비디오 목록 반환 (사용할 일이 거의 없음)
            return ResponseEntity.ok(getAllVideos());
        } else {
            // episodeId 해당하는 비디오 목록 반환
            return getVideosByEpisodeId(episodeId);
        }
    }

    public List<Video> getAllVideos() {
        return videoService.getAllVideos();  // 모든 비디오 데이터 반환
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Video>> getVideosByEpisodeId(@PathVariable("id") Long episodeId) {
        List<Video> videos = videoService.findVideosByEpisodeId(episodeId);
        if (videos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 비어 있으면 204 반환
        }
        return ResponseEntity.ok(videos);
    }
}
