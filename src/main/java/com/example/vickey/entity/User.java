package com.example.vickey.entity;

import jakarta.persistence.*;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(nullable = true)
    private String username;

    @Column(unique = true, nullable = true)
    private String email;

    @Column
    private String password;

    @Column(name = "profile_picture_url", columnDefinition = "LONGTEXT")
    private String profilePictureUrl;

    @Column(name = "signup_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime signupDate;

    @OneToOne
    @JoinColumn(name = "subscription")
    private Subscription subscription;



    public void updateSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @PrePersist
    protected void onCreate() {
        if (this.userId == null || this.userId.isEmpty()) {
            this.userId = java.util.UUID.randomUUID().toString(); // 기본적으로 UUID 설정
        }
        this.signupDate = LocalDateTime.now();
    }

    // Getters and setters
    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public LocalDateTime getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDateTime signupDate) {
        this.signupDate = signupDate;
    }
}
