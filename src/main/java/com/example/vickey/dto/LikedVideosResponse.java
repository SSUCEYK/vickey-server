package com.example.vickey.dto;

public class LikedVideosResponse {

    private Long videoId;
    private String thumbnailUrl;
    private Integer videoNum;

    // Constructor
    public LikedVideosResponse(Long videoId, String thumbnailUrl, Integer videoNum) {
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
        this.videoNum = videoNum;
    }

    // Getters and Setters
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

    public Integer getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(Integer videoNum) {
        this.videoNum = videoNum;
    }

}
