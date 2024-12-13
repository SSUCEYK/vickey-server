package com.example.vickey.api;

import com.example.vickey.dto.KakaoAuthRequest;
import com.example.vickey.dto.UserResponse;
import com.example.vickey.entity.User;
import com.example.vickey.service.KakaoService;
import com.example.vickey.service.NaverService;
import com.example.vickey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private KakaoService kakaoService;
    private NaverService naverService;
    private UserService userService;

    @Autowired
    public AuthController(KakaoService kakaoService, NaverService naverService, UserService userService) {
        this.kakaoService = kakaoService;
        this.naverService = naverService;
        this.userService = userService;
    }
    @PostMapping("/kakao-login")
    public ResponseEntity<Map<String, Object>> authenticateKakao(@RequestBody String accessToken) {

        System.out.println("KAKO Auth: Access Token=" + accessToken);
        UserResponse kakaoUser = kakaoService.verifyTokenAndGetUser(accessToken);
        User user = userService.findOrCreateUserById(kakaoUser.getUserId(), "", "", kakaoUser.getName());

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("profilePictureUrl", user.getProfilePictureUrl());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/naver-login")
    public ResponseEntity<UserResponse> naverLogin(@RequestBody String accessToken) {

        UserResponse naverUser = naverService.verifyTokenAndGetUser(accessToken);
        User user = userService.findOrCreateUserById(naverUser.getUserId(), "", "", naverUser.getName());
        boolean isSubscribed = user.getSubscription() != null;

        return ResponseEntity.ok(new UserResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getProfilePictureUrl(), isSubscribed));

    }
}
