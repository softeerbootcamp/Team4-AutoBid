package com.codesquad.autobid.handler.user;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.user.domain.UserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UserHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final RestTemplate rt = new RestTemplate();

    @Value("${hyundai.auth.token_profile_url}")
    private String TOKEN_PROFILE_URL;

    public UserVO userProfileAPICall(OauthToken oauthToken) {
        UserVO userVO = null;
        // HttpHeader 오브젝트 생성
        HttpHeaders headersForAccessToken = new HttpHeaders();
//        headersForAccessToken.add("Content-type", "application/x-www-form-urlencoded");
        headersForAccessToken.add("Authorization", "Bearer "+oauthToken.getAccessToken());
        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<HttpHeaders> hyundaiTokenRequest = new HttpEntity<>(headersForAccessToken);

        // 실제로 요청하기
        // Http 요청하기 - POST 방식으로 - 그리고 response 변수에 응답을 받음.
        ResponseEntity<String> userResponse = rt.exchange(
                TOKEN_PROFILE_URL,
                HttpMethod.GET,
                hyundaiTokenRequest,
                String.class
        );

        try {
            userVO = objectMapper.readValue(userResponse.getBody(), UserVO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userVO;
    }


}
