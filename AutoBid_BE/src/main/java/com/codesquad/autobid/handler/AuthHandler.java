package com.codesquad.autobid.handler;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.user.domain.UserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthHandler {

    private Logger logger = LoggerFactory.getLogger(AuthHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate rt = new RestTemplate();

    @Value("${hyundai.auth.redirect_uri}")
    private String REDIRECT_URI;

    @Value("${hyundai.auth.authorization_key}")
    private String AUTHORIZATION_KEY;

    @Value("${hyundai.auth.token_request_uri}")
    private String TOKEN_REQUEST_URI;

    @Value("${hyundai.auth.token_profile_url}")
    private String TOKEN_PROFILE_URL;

    public OauthToken getOauthToken(OauthType oauthType, String value) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", oauthType.getGrantType()); // 고정값
        params.add("redirect_uri", REDIRECT_URI);
        params.add(oauthType.getTokenType(), value);

        // JSON 응답을 객체로 변환
        OauthToken oauthToken = null;

        // HttpHeader 오브젝트 생성
        HttpHeaders headersForAccessToken = new HttpHeaders();
        headersForAccessToken.add("Content-type", "application/x-www-form-urlencoded");
        headersForAccessToken.add("Authorization", AUTHORIZATION_KEY);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> hyundaiTokenRequest = new HttpEntity<>(params, headersForAccessToken);

        // 실제로 요청하기
        // Http 요청하기 - POST 방식으로 - 그리고 response 변수에 응답을 받음.
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                TOKEN_REQUEST_URI,
                HttpMethod.POST,
                hyundaiTokenRequest,
                String.class
        );

        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

    public OauthToken getOauthDeleteToken(OauthType oauthType, String value) {
//        grant_type=delete&access_token={access_token}&redirect_uri={redirect_uri}
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", oauthType.getGrantType()); // 고정값
        params.add("redirect_uri", REDIRECT_URI);
        params.add(oauthType.getTokenType(), value);

        // JSON 응답을 객체로 변환
        OauthToken oauthToken = null;

        // HttpHeader 오브젝트 생성
        HttpHeaders headersForAccessToken = new HttpHeaders();
        headersForAccessToken.add("Content-type", "application/x-www-form-urlencoded");
        headersForAccessToken.add("Authorization", AUTHORIZATION_KEY);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> hyundaiTokenRequest = new HttpEntity<>(params, headersForAccessToken);

        // 실제로 요청하기
        // Http 요청하기 - POST 방식으로 - 그리고 response 변수에 응답을 받음.
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                TOKEN_REQUEST_URI,
                HttpMethod.POST,
                hyundaiTokenRequest,
                String.class
        );

        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

    public OauthToken getOauthRefreshToken(OauthType oauthType, String value) {
//        grant_type=delete&access_token={access_token}&redirect_uri={redirect_uri}
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", oauthType.getGrantType()); // 고정값
        params.add("redirect_uri", REDIRECT_URI);
        params.add(oauthType.getTokenType(), value);

        // JSON 응답을 객체로 변환
        OauthToken oauthToken = null;

        // HttpHeader 오브젝트 생성
        HttpHeaders headersForAccessToken = new HttpHeaders();
        headersForAccessToken.add("Content-type", "application/x-www-form-urlencoded");
        headersForAccessToken.add("Authorization", AUTHORIZATION_KEY);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> hyundaiTokenRequest = new HttpEntity<>(params, headersForAccessToken);

        // 실제로 요청하기
        // Http 요청하기 - POST 방식으로 - 그리고 response 변수에 응답을 받음.
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                TOKEN_REQUEST_URI,
                HttpMethod.POST,
                hyundaiTokenRequest,
                String.class
        );

        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

}
