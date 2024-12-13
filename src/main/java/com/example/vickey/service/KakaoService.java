package com.example.vickey.service;


import com.example.vickey.dto.KakaoApiResponse;
import com.example.vickey.dto.UserResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {

    private final RestTemplate restTemplate;

    public KakaoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserResponse verifyTokenAndGetUser(String accessToken) {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // 카카오 API 호출
        ResponseEntity<KakaoApiResponse> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                httpEntity,
                KakaoApiResponse.class
        );

        // 응답 데이터 매핑
        KakaoApiResponse apiResponse = response.getBody();

        if (apiResponse != null) {

            System.out.println("KAKAO Response: "
                    + apiResponse.getId() + ", "
                    + apiResponse.getNickname() + ", "
                    + apiResponse.getProfileImageUrl());


            return new UserResponse(
                    apiResponse.getId(),
                    apiResponse.getNickname(),
                    "", //Email X
                    apiResponse.getProfileImageUrl(),
                    false //무관
            );
        } else {
            throw new RuntimeException("Failed to retrieve user info from Kakao API.");
        }
    }
}
