package com.example.vickey.entity;

import com.example.vickey.repository.EpisodeRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY: DB에서 AUTO_INCREMENT 사용
    @Column(name = "video_id")
    private Long videoId;

    @ManyToOne
    @JoinColumn(name = "episode_id", nullable = false) //외래키 설정
    @JsonBackReference // 부모 엔티티를 직렬화에서 제외
    private Episode episode;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    private int duration;

    @Column(name = "video_num", nullable = false)
    private int videoNum;

    @Column(name = "thumbnail_url", nullable = false, columnDefinition = "LONGTEXT")
    private String thumbnailUrl;



    // Getters 및 Setters

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public void setEpisodeId(Long episodeId, EpisodeRepository episodeRepository) {
        this.episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Episode ID: " + episodeId));
    }

    public Long getEpisodeId() {
        return this.episode != null ? this.episode.getEpisodeId() : null;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Video{" +
                "videoId=" + videoId +
                ", episodeId=" + (episode != null ? episode.getEpisodeId() : null) +
                ", videoUrl='" + videoUrl + '\'' +
                ", duration=" + duration +
                ", videoNum=" + videoNum +
                '}';
    }
}

