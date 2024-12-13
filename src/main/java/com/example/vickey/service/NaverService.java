package com.example.vickey.service;

import com.example.vickey.dto.UserResponse;
import com.example.vickey.dto.NaverApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NaverService {

    private final RestTemplate restTemplate;

    public NaverService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserResponse verifyTokenAndGetUser(String accessToken) {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // 네이버 API 호출
        String apiUrl = "https://openapi.naver.com/v1/nid/me";
        ResponseEntity<NaverApiResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                httpEntity,
                NaverApiResponse.class
        );

        // 응답 데이터 매핑
        NaverApiResponse apiResponse = response.getBody();
        if (apiResponse != null && apiResponse.getResponse() != null) {

            System.out.println("NAVER Response: "
                    + apiResponse.getResponse().getId() + ", "
                    + apiResponse.getResponse().getName());

            return new UserResponse(
                    apiResponse.getResponse().getId(),
                    apiResponse.getResponse().getName(),
                    "", //이메일 사용 동의를 받고 나서 사용 가능
                    "", //프로필 이미지 사용 동의를 받고 나서 사용 가능
                    false // 구독 여부는 무관하므로 기본값 설정
            );
        } else {
            throw new RuntimeException("Failed to retrieve user info from Naver API.");
        }
    }
}
