package com.example.vickey.entity;

import com.example.vickey.CheckWatchedKey;
import com.example.vickey.entity.User;
import com.example.vickey.entity.Video;
import jakarta.persistence.*;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Check_watched")
public class CheckWatched {

    @EmbeddedId
    private CheckWatchedKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("videoId")
    @JoinColumn(name = "video_id", referencedColumnName = "video_id")
    private Video video;

    @Column(nullable = false)
    private Integer progress = 0; //default값 설정

    @Column(name = "watched_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime watchedDate;
    
    @PrePersist
    protected void onCreate() {
        if (this.watchedDate == null) {
            this.watchedDate = LocalDateTime.now();
        }
    }

    // Getters and setters

    public CheckWatchedKey getId() {
        return id;
    }

    public void setId(CheckWatchedKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public LocalDateTime getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(LocalDateTime watchedDate) {
        this.watchedDate = watchedDate;
    }
}
