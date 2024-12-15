package com.example.vickey.api;

import com.example.vickey.dto.LoginRequest;
import com.example.vickey.dto.SignupRequest;
import com.example.vickey.dto.UserResponse;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.Subscription;
import com.example.vickey.entity.User;
import com.example.vickey.repository.SubscriptionRepository;
import com.example.vickey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    // 결제 처리
    @GetMapping("/status")
    public ResponseEntity<UserStatusResponse> getUserStatus(@RequestParam("uid") String uid) {

        System.out.println("UserController.getUserStatus/ uid="+uid);

        User user = (User)userService.getUserById(uid).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("UserController.getUserStatus/ user.get()="+user.getUserId()
                    + ", " + user.getEmail() + ", " +user.getUsername());

            boolean isSubscribed = user.getSubscription() != null;
            System.out.println("UserController.getUserStatus/ user isSubscribed?=" + isSubscribed);

            return ResponseEntity.ok(new UserStatusResponse(this, isSubscribed));
        }
    }

    class UserStatusResponse {
        private boolean Subscribed;

        public UserStatusResponse(final UserController this$0, boolean isSubscribed) {
            this.Subscribed = isSubscribed;
        }

        public boolean isSubscribed() {
            return this.Subscribed;
        }

        public void setSubscribed(boolean subscribed) {
            this.Subscribed = subscribed;
        }
    }


    // 이메일 로그인 처리
    @PostMapping("/email-login")
    public ResponseEntity<UserResponse> emailLogin(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String name = email.substring(0, email.indexOf("@"));

        System.out.println("email-login: email=" + email + ", name=" + name);

        User user = userService.findOrCreateUser(loginRequest.getUid(), email, "", name);

        boolean isSubscribed = user.getSubscription() != null;

        return ResponseEntity.ok(new UserResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getProfilePictureUrl(), isSubscribed));
    }

    // 이메일 가입 처리
    @PostMapping("/email-signup")
    public ResponseEntity<Map<String, Object>> emailSignup(@RequestBody SignupRequest signupRequest) {
        String uid = signupRequest.getUid();
        String email = signupRequest.getEmail();

        User newUser = userService.createEmailUser(uid, email, "");

        Map<String, Object> response = new HashMap<>();
        response.put("userId", newUser.getUserId());
        response.put("email", newUser.getEmail());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{userId}/username")
    public ResponseEntity<Void> updateUsername(@PathVariable String userId, @RequestBody String newUsername) {
        userService.updateUsername(userId, newUsername);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/profile-image")
    public ResponseEntity<Map<String, String>> updateProfileImage(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
        try {
            String url = userService.updateProfileImage(userId, file);
            Map<String, String> response = new HashMap<>();
            response.put("profileImageUrl", url);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
