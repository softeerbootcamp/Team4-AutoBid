package com.codesquad.autobid.handler.user;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.user.domain.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Component
public class UserHandler {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final RestTemplate rt = new RestTemplate();

    @Value("${hyundai.auth.token_profile_url}")
    private String PROFILE_URI;

    public UserVO userProfileAPICall(OauthToken oauthToken) {    // 발급받은 Access Token
        String access_token = oauthToken.getAccessToken();
        UserVO user = new UserVO();
        try {
            URL url = new URL(PROFILE_URI);
            String parsingData = UrlReader.reader(url,access_token);
            try {
                user = mapper.readValue(parsingData, UserVO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }


}
