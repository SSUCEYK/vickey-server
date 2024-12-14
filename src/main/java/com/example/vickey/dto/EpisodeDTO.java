package com.example.vickey.dto;


import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Video;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EpisodeDTO {
    private long episodeId;
    private String title;
    private String thumbnailUrl;
    private int episodeCount;
    private String description;
    private String releasedDate;
    private String castList;
    private List<String> videoUrls;
    private List<Long> videoIds;

    public EpisodeDTO(Episode episode) {
        this.episodeId = episode.getEpisodeId();
        this.title = episode.getTitle();
        this.thumbnailUrl = episode.getThumbnailUrl();
        this.episodeCount = episode.getEpisodeCount();
        this.description = episode.getDescription();
        this.releasedDate = episode.getReleasedDate();
        this.castList = episode.getCastList();

        this.videoUrls = episode.getVideos() != null
                ? episode.getVideos().stream().map(Video::getVideoUrl).collect(Collectors.toList())
                : Collections.emptyList();

        this.videoIds = episode.getVideos() != null
                ? episode.getVideos().stream().map(Video::getVideoId).collect(Collectors.toList())
                : Collections.emptyList();
    }

    public long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getCastList() {
        return castList;
    }

    public void setCastList(String castList) {
        this.castList = castList;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    public List<Long> getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(List<Long> videoIds) {
        this.videoIds = videoIds;
    }
}

