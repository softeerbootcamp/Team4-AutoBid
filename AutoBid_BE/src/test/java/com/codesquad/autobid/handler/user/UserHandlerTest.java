package com.codesquad.autobid.handler.user;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.auth.service.AuthService;
import com.codesquad.autobid.handler.OauthType;
import com.codesquad.autobid.user.domain.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserHandlerTest {

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    @Value("${hyundai.auth.token_profile_url}")
    private String APIURL;

    @Value("${hyundai.auth.redirect_uri}")
    private String REDIRECT_URI;

    @Value("${hyundai.auth.authorization_key}")
    private String AUTHORIZATION_KEY;

    @Value("${hyundai.auth.token_request_uri}")
    private String TOKEN_REQUEST_URI;

    private String access_token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaWQiOiI1YThhMjczMWY1ZmYzMjI4MWNjYTJhNTMiLCJ1aWQiOiIyMDQ3MjFiNS1mNDJiLTQ0NDUtOTg4Yy1hZGNiZGUwY2YxN2UiLCJzaWQiOiJkMjVlZWY2OC0wNDNkLTQ2MDEtYjFjNi0xMTAxYmI0MzEzZjUiLCJleHAiOjE2NzU5MjA0MTksImlhdCI6MTY3NTgzNDAxOSwiaXNzIjoiYmx1ZWxpbmsifQ.NdrudbsEkR3iK4OGIJG4Gq9Rs02dR3TvjrlgVkawfYt1ZLNNhEHmqjXdlQcOmsORNt-j19GcDhqyNbkwX4EJFSg1ZHfGjCsBKanrQ-2pWzBIsWSgNaPjhp9vDwvupgiieo-jSKevpeP6mROEoGZOBgaN4udd2TmLKhu1As1qVmDqHPr8rFMH7H1_Nr7wS1yisXqRFsLXCJf6-GGz9IcNY0LroED6uB1A4aGBjy69JS50qZJ2dDZpVMt38faqOf3K5v3JCjQbYEvSclnOusq8d77ixvMRb9FuQrR-1m2KFKnRL3Zk0v_gEStmkl9BRfNG0aS5DOCzsCVfXoaxxYoK3w"; // 발급받은 Access Token
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final RestTemplate rt = new RestTemplate();

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("리프레쉬 토큰으로 재발급")
    void refresh_token() {
        OauthType oauthType = OauthType.REFRESH;
        String refrsh_token = "KQGP09-EWBAQKDVJXH7UHQ";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", oauthType.getGrantType()); // 고정값
            params.add(oauthType.getTokenType(), refrsh_token);
            params.add("redirect_uri", REDIRECT_URI);

            OauthToken oauthToken = null;

            HttpHeaders headersForAccessToken = new HttpHeaders();
            headersForAccessToken.add("Content-type", "application/x-www-form-urlencoded");
            headersForAccessToken.add("Authorization", AUTHORIZATION_KEY);

            HttpEntity<MultiValueMap<String, String>> hyundaiTokenRequest = new HttpEntity<>(params, headersForAccessToken);

            ResponseEntity<String> accessTokenResponse = rt.exchange(
                    TOKEN_REQUEST_URI,
                    HttpMethod.POST,
                    hyundaiTokenRequest,
                    String.class
            );

            try {
                oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            log.info("access : {}",oauthToken.getAccessToken());
            log.info("refresh : {}",oauthToken.getRefreshToken());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    @DisplayName("사용자 정보 조회")
    void profile_data() {

    }
}
