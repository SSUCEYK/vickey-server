package com.example.vickey.dto;

import com.example.vickey.entity.CheckWatched;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Video;

import java.util.Optional;

public class CheckWatchedResponse {

    private Long videoId;
    private String thumbnailUrl;
    private int videoNum;
    private Integer progress;
    private Long episodeId;
    private String episodeTitle;

    // 기본 생성자
    public CheckWatchedResponse() {}

    // CheckWatched 엔터티 기반 생성자
    public CheckWatchedResponse(CheckWatched checkWatched) {
        Video video = checkWatched.getVideo();
        this.videoId = video.getVideoId();
        this.thumbnailUrl = video.getThumbnailUrl();
        this.videoNum = video.getVideoNum();
        this.progress = checkWatched.getProgress();
    }

    // JPQL 쿼리에서 사용하는 생성자
    public CheckWatchedResponse(Long videoId, Long episodeId, String thumbnailUrl, int videoNum, Integer progress, String episodeTitle) {
        this.videoId = videoId;
        this.episodeId = episodeId;
        this.thumbnailUrl = thumbnailUrl;
        this.videoNum = videoNum;
        this.progress = progress;
        this.episodeTitle = episodeTitle;
    }


    // Getter와 Setter
    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }
}

//
//public class CheckWatchedResponse {
//
//    private Long videoId;
//    private String thumbnailUrl;
//    private int videoNum;
//    private Integer progress;
//    private Optional<Episode> episode;
//
//    public CheckWatchedResponse(CheckWatched checkWatched) {
//        Video video = checkWatched.getVideo();
//        this.videoId = video.getVideoId();
//        this.thumbnailUrl = video.getThumbnailUrl();
//        this.videoNum = video.getVideoNum();
//        this.progress = checkWatched.getProgress();
//    }
//
//    public int getVideoNum() {
//        return videoNum;
//    }
//
//    public void setVideoNum(int videoNum) {
//        this.videoNum = videoNum;
//    }
//
//    public Long getVideoId() {
//        return videoId;
//    }
//
//    public String getThumbnailUrl() {
//        return thumbnailUrl;
//    }
//
//    public Integer getProgress() {
//        return progress;
//    }
//
//    public void setVideoId(Long videoId) {
//        this.videoId = videoId;
//    }
//
//    public void setThumbnailUrl(String thumbnailUrl) {
//        this.thumbnailUrl = thumbnailUrl;
//    }
//
//    public void setProgress(Integer progress) {
//        this.progress = progress;
//    }
//
//    public Optional<Episode> getEpisode() {
//        return episode;
//    }
//
//    public void setEpisode(Optional<Episode> episode) {
//        this.episode = episode;
//    }
//}
