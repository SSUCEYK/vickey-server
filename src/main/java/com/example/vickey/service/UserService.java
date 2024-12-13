package com.example.vickey.service;

import com.example.vickey.S3Service;
import com.example.vickey.entity.Subscription;
import com.example.vickey.entity.User;
import com.example.vickey.repository.SubscriptionRepository;
import com.example.vickey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final SubscriptionRepository subscriptionRepository;
    private final S3Service s3Service;

    @Autowired
    public UserService(UserRepository userRepository, SubscriptionRepository subscriptionRepository, S3Service s3Service) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.s3Service = s3Service;
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

    public User findOrCreateUserById(String uid, String email, String profilePictureUrl, String name) {
        Optional<User> userOptional = userRepository.findById(uid);

        if (userOptional.isPresent()) {
            return userOptional.get();

        } else {
            User newUser = new User();
            newUser.setUserId(uid);
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setProfilePictureUrl(profilePictureUrl);
            newUser.setPassword(""); // 저장X

            return userRepository.save(newUser);
        }
    }

    public User findOrCreateUser(String uid, String email, String profilePictureUrl, String name) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();

        } else {
            User newUser = new User();
            newUser.setUserId(uid);
            newUser.setEmail(email);
            newUser.setPassword(""); // 저장X
            newUser.setProfilePictureUrl(profilePictureUrl);

            if (!Objects.equals(name, "")){
                //name 입력 안들어오면 default값으로 설정
                newUser.setUsername(name); 
            }
            
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
        return userRepository.save(newUser);
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUsername(String userId, String newUsername) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    public String updateProfileImage(String userId, MultipartFile imageFile) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        try {
            String url = s3Service.uploadImg(imageFile, "profile");
            System.out.println("Uploaded ProfileImage URL: " + url);
            user.setProfilePictureUrl(url);
            userRepository.save(user);
            return url;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload thumbnail to S3: " + e.getMessage(), e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}

