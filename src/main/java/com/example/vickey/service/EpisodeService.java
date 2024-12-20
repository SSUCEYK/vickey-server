package com.example.vickey.service;

import com.example.vickey.S3Service;
import com.example.vickey.entity.Episode;
import com.example.vickey.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//EpisodeRepository에서 제공하는 메소드만 호출하고, 비즈니스 로직 처리
@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final S3Service s3Service;

    @Autowired
    public EpisodeService(EpisodeRepository episodeRepository, S3Service s3Service) {
        this.episodeRepository = episodeRepository;
        this.s3Service = s3Service;
    }

    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    public Episode addEpisode(Episode episode) {
        System.out.println("EpisodeService/ Received Episode in addEpisode: " + episode);
        System.out.println("EpisodeService/ AddEpisode.thumbnailUrl:" + episode.getThumbnailUrl());
        return episodeRepository.save(episode);
    }

    public String uploadThumbnail(MultipartFile file) {
        try {
            String url = s3Service.uploadImg(file, "thumbnail");
            System.out.println("Uploaded Thumbnail URL: " + url);
            return url;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload thumbnail to S3: " + e.getMessage(), e);
        }
    }

    public Optional<Episode> getEpisodeById(Long id) {
        return episodeRepository.findById(id);
    }


    public List<Long> getRandomEpisodeIds(int n) {
        List<Episode> allEpisodes = episodeRepository.findAll();
        Collections.shuffle(allEpisodes);
        return allEpisodes.stream()
                .limit(n)
                .map(Episode::getEpisodeId)
                .collect(Collectors.toList());
    }


    public List<String> getEpisodeThumbnails(List<Long> ids) {
        return episodeRepository.findAllById(ids).stream()
                .map(Episode::getThumbnailUrl)
                .collect(Collectors.toList());
    }


    public List<Episode> getRandomEpisodes(int n) {
        return episodeRepository.findRandomEpisodes(n);
    }

    public List<Episode> getTopNEpisodesByLikedCount(int n) {
        return episodeRepository.findTopNEpisodesByLikeCount(n);
    }

    public List<Episode> getTopNEpisodesByWatchedCount(int n) {
        return episodeRepository.findTopNEpisodesByWatchCount(n);
    }

    public List<Episode> searchEpisodes(String searchQuery) {
        return episodeRepository.searchEpisodes(searchQuery);
    }

    public Episode contentInfoEpisode(long contentInfoQuery) {
        Episode episode = episodeRepository.findEpisodeWithVideos(contentInfoQuery); //Lazy Loading 대신 Fetch Join 사용
        System.out.println("contentInfoEpisodes: Video="+episode.getVideos());
        System.out.println("contentInfoEpisodes: VideoURLs=" + episode.getVideoUrls());

        return episode;
    }

}
