package com.example.vickey.api;

import com.example.vickey.dto.LoginRequest;
import com.example.vickey.dto.SignupRequest;
import com.example.vickey.entity.Episode;
import com.example.vickey.entity.User;
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


    // 카카오, 네이버 로그인 처리
    @PostMapping("/social-login")
    public ResponseEntity<Map<String, Object>> socialLogin(
            @RequestParam String email,
            @RequestParam(required = false) String profilePictureUrl) {

        String uid = ""; // 수정
        User user = userService.findOrCreateUser(uid, email, profilePictureUrl);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("profilePictureUrl", user.getProfilePictureUrl());

        return ResponseEntity.ok(response);
    }

    // 이메일 로그인 처리
    @PostMapping("/email-login")
    public ResponseEntity<Map<String, Object>> emailLogin(@RequestBody LoginRequest loginRequest) {
        String uid = loginRequest.getUid();
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println("emailLogin: email="+email);
        System.out.println("emailLogin: password="+password);

        // 1. User 조회
        User user = userService.findUserByEmail(email);

        if (user != null) {
            // 2-1. User 존재 시 PW 체크
//            if (userService.validatePassword(password, user.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("isSubscribed", user.getSubscription() != null); //api 중복 호출되지 않도록 로그인 시 같이 정보 반환함

            return ResponseEntity.ok(response);
//            }
//            else{
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid password"));
//            }
        }
        else{
            // 2-2. User 부재 시 생성
            User newUser = userService.createEmailUser(uid, email, "");
            Map<String, Object> response = new HashMap<>();
            response.put("userId", newUser.getUserId());
            response.put("username", newUser.getUsername());
            response.put("email", newUser.getEmail());
            response.put("message", "User created and logged in");

            // 가입 시에 자동으로 subscription 추가하게 되어있기 때문에 바로 False 반환하기
            // boolean isSubscribed = user.getSubscription() != null;
            response.put("isSubscribed", false);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    }

    // 이메일 가입 처리
    @PostMapping("/email-signup")
    public ResponseEntity<Map<String, Object>> emailSignup(@RequestBody SignupRequest signupRequest) {
        String uid = signupRequest.getUid();
        String email = signupRequest.getEmail();
        String password = signupRequest.getPassword();

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
    public ResponseEntity<String> updateProfileImage(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(userService.updateProfileImage(userId, file));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
