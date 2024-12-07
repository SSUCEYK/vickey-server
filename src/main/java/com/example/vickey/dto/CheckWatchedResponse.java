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
    private Optional<Episode> episode;

    public CheckWatchedResponse(CheckWatched checkWatched) {
        Video video = checkWatched.getVideo();
        this.videoId = video.getVideoId();
        this.thumbnailUrl = video.getThumbnailUrl();
        this.videoNum = video.getVideoNum();
        this.progress = checkWatched.getProgress();
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Optional<Episode> getEpisode() {
        return episode;
    }

    public void setEpisode(Optional<Episode> episode) {
        this.episode = episode;
    }
}
