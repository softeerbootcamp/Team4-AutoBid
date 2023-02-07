package com.codesquad.autobid.handler.user;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.user.domain.UserVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UserHandler {

    @Value("${hyundai.auth.redirect_uri}")
    private String redirectURL;

    @Value("${hyundai.auth.token_request_uri}")
    private String apiURL;

    @Value("${hyundai.auth.authorization_key}")
    private String token;

    String access_token = "";
    String refresh_token = "";

    public UserVO userProfileAPICall(OauthToken oauthToken) {    // 발급받은 Access Token
        access_token = oauthToken.getAccessToken();
        refresh_token = oauthToken.getRefreshToken();

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

    // 2. 계정 API 예제 – 토큰 갱신
    public String tokenRefresh() throws IOException{

        // AccessToken 발급시에 받은 RefreshToken 사용
        String requestBody = "grant_type=refresh_token&refresh_token=" + refresh_token + "&redirect_uri=" + redirectURL;
        String tokenResponse = tokenAPICall(requestBody);

        ObjectMapper accessTokenObjectMapper = new ObjectMapper();
        JsonNode TokenRoot = accessTokenObjectMapper.readTree(tokenResponse);
        String accessToken = TokenRoot.path("access_token").asText();
        System.out.println("갱신된 accessToken = " + accessToken);

        return accessToken;
    }

    public String tokenAPICall(String requestBody){

        StringBuffer sb = null;
        String responseData = "";
        String contentType = "application/x-www-form-urlencoded";

        try{
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");

            // Set Header Info
            con.setRequestProperty("Authorization", token);
            con.setRequestProperty("Content-Type", contentType);

            // Body data 전송
            con.setDoOutput(true);
            try (DataOutputStream output = new DataOutputStream(con.getOutputStream())){
                output.writeBytes(requestBody);
                output.flush();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            int responseCode = con.getResponseCode();

            BufferedReader br;
            if(responseCode == HttpURLConnection.HTTP_OK){
                br = new BufferedReader(new InputStreamReader(con.getInputStream())); // 정상호출
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream())); // 에러발생
            }

            sb = new StringBuffer();
            while ((responseData = br.readLine()) != null){
                sb.append(responseData);
            }
            br.close();

            System.out.println("responseCode = " + responseCode);
            System.out.println("responseData = " + sb.toString());

        } catch (Exception e) {
            System.out.println(e);
        }

        return sb.toString();
    }
}
