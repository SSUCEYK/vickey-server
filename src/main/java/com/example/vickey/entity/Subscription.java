package com.example.vickey.entity;

import com.example.vickey.SubscriptionType;
import jakarta.persistence.*;

import java.time.LocalDate;
import com.querydsl.core.annotations.QueryEntity;

//@QueryEntity
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "subscription")
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    public Subscription(User user, SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
        this.user = user;
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(31L);
    }

    public void updateDate() {
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(31L);
    }

    public void updateType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}

