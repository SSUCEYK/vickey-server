package com.example.vickey.service;

import com.example.vickey.S3Service;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Video;
import com.example.vickey.repository.EpisodeRepository;
import com.example.vickey.repository.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final EpisodeRepository episodeRepository;
    private final S3Service s3Service;

    @Autowired
    public VideoService(VideoRepository videoRepository, EpisodeRepository episodeRepository, S3Service s3Service) {
        this.videoRepository = videoRepository;
        this.episodeRepository = episodeRepository;
        this.s3Service = s3Service;
    }

    public Map<String, Object> uploadVideo(MultipartFile file) throws IOException {
        return s3Service.uploadFile(file); // S3에 업로드하고 URL 반환
    }

    // 비디오 정보를 DB에 저장
    @Transactional
    public void saveVideo(Long episodeId, String videoUrl, int duration) {

        // 1. 해당 에피소드 ID로 Episode 가져오기
//        Episode episode = episodeRepository.findById(episodeId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Episode ID: " + episodeId));

        // 2. 현재 에피소드에서 최대 videoNum 가져오기
        Integer maxVideoNum = videoRepository.findMaxVideoNumByEpisodeId(episodeId);

        // 3. 다음 videoNum 계산
        int nextVideoNum = (maxVideoNum == null) ? 1 : maxVideoNum + 1;

        // 4. Video 엔티티 생성
        Video video = new Video();
        video.setEpisodeId(episodeId, episodeRepository);
        video.setVideoUrl(videoUrl);
        video.setVideoNum(nextVideoNum);
        video.setDuration(duration);

        System.out.println("Video to be saved: " + video);

        // 5. DB에 저장
        // videoRepository.save(video);
        try {
            videoRepository.save(video);
            System.out.println("Video saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save video: " + e.getMessage());
        }
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public List<Video> findVideosByEpisodeId(Long episodeId) {
        return videoRepository.findByEpisodeId(episodeId);
    }
}

