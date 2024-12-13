package com.example.vickey;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class LikeKey implements Serializable {

    private String userId;
    private Long videoId;

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeKey likeKey = (LikeKey) o;
        return Objects.equals(userId, likeKey.userId) &&
                Objects.equals(videoId, likeKey.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, videoId);
    }
}
