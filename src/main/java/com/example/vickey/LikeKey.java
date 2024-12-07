package com.example.vickey;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class LikeKey implements Serializable {

    private Long userId;
    private Long videoId;

    // equals() and hashCode()
}
