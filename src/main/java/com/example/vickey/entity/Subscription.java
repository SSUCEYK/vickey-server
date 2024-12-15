package com.example.vickey.entity;

import com.example.vickey.SubscriptionType;
import jakarta.persistence.*;

import java.time.LocalDate;
import com.querydsl.core.annotations.QueryEntity;

//@QueryEntity
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "subscription")
    private User user;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    private SubscriptionType subscriptionType;

    public Subscription(User user, SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
        this.user = user;
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(31L);
    }

    public Subscription() {

    }

    public int getSubscriptionPrice() {
        return this.subscriptionType.getPrice();
    }

    public String getSubscriptionName() {
        return this.subscriptionType.getName();
    }

    public void updateDate() {
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(31L);
    }

    public void updateType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}

