package com.example.vickey.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 자식 엔티티를 직렬화
    private List<Video> videos = new ArrayList<>();

    @Transient
    private List<String> videoUrls; // Transient 필드로 videoUrls 추가 (DB 매핑X, JPA 관리 엔티티 필드에 포함X, videos 리스트에서 videoUrl 필드를 추출해 동적으로 제공)

    @Column(name = "like_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int likeCount;

    @Column(name = "watch_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int watchCount;


    // Getters 및 Setters
    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(int watchCount) {
        this.watchCount = watchCount;
    }

    public List<String> getVideoUrls() {
        if (this.videoUrls == null) {
            this.videoUrls = this.videos.stream()
                    .map(Video::getVideoUrl)
                    .collect(Collectors.toList());
        }
        return videoUrls;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

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
}
