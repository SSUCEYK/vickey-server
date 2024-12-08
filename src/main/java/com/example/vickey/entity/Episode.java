package com.example.vickey.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// 컨텐츠 (==episode; 처음 설계 시 이름을 헷갈리게 지정했으니 주의)
@Entity
@Table(name = "episode")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동 생성
    @Column(name = "episode_id")
    private Long episodeId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "episode_count", nullable = false)
    private Integer episodeCount;

    @Column(name = "description")
    private String description;

    @Column(name = "released_date")
    private String releasedDate;

    @Column(name = "thumbnail_url", columnDefinition = "LONGTEXT")
    private String thumbnailUrl;

    @Column(name = "cast_list")
    private String castList;

    @Column(name = "video_urls", columnDefinition = "json")
    private String videoURLs; //json 문자열 형식의 모든 영상 주소

//    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Video> videos = new ArrayList<>();




// Getters 및 Setters

    public Long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCastList() {
        return castList;
    }

    public void setCastList(String castList) {
        this.castList = castList;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }
    public String getVideoURLs() {
        return videoURLs;
    }

    public void setVideoURLs(String videoURLs) {
        this.videoURLs = videoURLs;
    }
}
