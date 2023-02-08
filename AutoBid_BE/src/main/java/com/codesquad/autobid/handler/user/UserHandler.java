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
    private String apiURL;

    public UserVO userProfileAPICall(OauthToken oauthToken) {    // 발급받은 Access Token
        String access_token = oauthToken.getAccessToken();
        UserVO user = new UserVO();
        StringBuffer sb;
        String responseData = "";

        try {
            URL url = new URL(apiURL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            // Set Header Info
            con.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = con.getResponseCode();
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
            System.out.println(e);
        }
        return user;
    }


}
