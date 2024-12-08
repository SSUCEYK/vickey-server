package com.example.vickey.service;

import com.example.vickey.entity.Subscription;
import com.example.vickey.entity.User;
import com.example.vickey.repository.SubscriptionRepository;
import com.example.vickey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public UserService(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public void updateUserSubscription(String userId, Subscription subscription) {
        Subscription savedSubscription = (Subscription)subscriptionRepository.save(subscription);
        User user = (User)userRepository.findById(userId).orElseThrow(() -> {
            return new RuntimeException("User not found");
        });
        user.updateSubscribe(savedSubscription);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findOrCreateUser(String uid, String email, String profilePictureUrl) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();

        } else {
            User newUser = new User();
            newUser.setUserId(uid);
            newUser.setEmail(email);
            newUser.setPassword(""); // 소셜 로그인 사용자는 비밀번호 필요 없음
            newUser.setProfilePictureUrl(profilePictureUrl);

            return userRepository.save(newUser);
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User createEmailUser(String uid, String email, String password) {
        User newUser = new User();
        newUser.setUserId(uid);
        newUser.setUsername(email.split("@")[0]); // 이메일 앞부분을 기본 username으로 사용
        newUser.setEmail(email);
        newUser.setPassword("");
//        newUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(newUser);
    }

//    public boolean validatePassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
