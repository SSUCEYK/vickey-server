package com.example.vickey.service;


import com.example.vickey.SubscriptionType;
import com.example.vickey.entity.Subscription;
import com.example.vickey.entity.User;
import com.example.vickey.repository.SubscriptionRepository;
import com.example.vickey.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired //@Generated
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createOrUpdateSubscription(String uid, SubscriptionType subscriptionType) {
        User user = (User)userRepository.findById(uid).orElseThrow(() -> {
            return new IllegalArgumentException("User not found with UID: " + uid);
        });

        Subscription subscription = user.getSubscription();
        if (subscription == null) {
            subscription = new Subscription(user, subscriptionType);
            user.updateSubscribe(subscription);
            subscriptionRepository.save(subscription);
        } else {
            subscription.updateType(subscriptionType);
            subscription.updateDate();
        }

        userRepository.save(user);
    }

    public Subscription getSubscriptionByUserId(String uid) {
        User user = (User)this.userRepository.findById(uid).orElseThrow(() -> {
            return new IllegalArgumentException("User not found with UID: " + uid);
        });
        return user.getSubscription();
    }

}

