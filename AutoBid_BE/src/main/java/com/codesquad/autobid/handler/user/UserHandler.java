package com.codesquad.autobid.handler.user;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.user.domain.UserVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class UserHandler {

    @Value("${hyundai.auth.redirect_uri}")
    private String redirectURL;

    @Value("${hyundai.auth.token_request_uri}")
    private String apiURL;

    @Value("${hyundai.auth.authorization_key}")
    private String token;

    public UserVO userProfileAPICall(OauthToken oauthToken) {    // 발급받은 Access Token
        String access_token = oauthToken.getAccessToken();
        String refresh_token = oauthToken.getRefreshToken();

        ObjectMapper mapper = new ObjectMapper();
        UserVO user = new UserVO();
        StringBuffer sb;
        String responseData = "";

        try {
            String apiURL = "https://prd.kr-ccapi.hyundai.com/api/v1/user/profile";
            URL url = new URL(apiURL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            // Set Header Info
            con.setRequestProperty("Authorization", "Bearer " + access_token);

            BufferedReader br;
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream())); // 정상호출
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream())); // 에러발생
            }

            sb = new StringBuffer();
            while ((responseData = br.readLine()) != null) {
                sb.append(responseData);
            }

            br.close();

            try {
                user = mapper.readValue(sb.toString(), UserVO.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return user;
    }
}
