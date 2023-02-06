package com.codesquad.autobid.handler.user;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.user.domain.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UserHandler {

    public UserVO userProfileAPICall(OauthToken oauthToken) {    // 발급받은 Access Token
        String access_token = oauthToken.getAccess_token();
        String refresh_token = oauthToken.getRefresh_token();

        ObjectMapper mapper = new ObjectMapper();
        UserVO user = new UserVO();
        StringBuffer sb;
        String responseData = "";

        try{
            String apiURL = "https://prd.kr-ccapi.hyundai.com/api/v1/user/profile";
            URL url = new URL(apiURL);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("GET");

            // Set Header Info
            con.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                br = new BufferedReader(new InputStreamReader(con.getInputStream())); // 정상호출
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream())); // 에러발생
            }

            sb = new StringBuffer();
            while ((responseData = br.readLine()) != null){
                sb.append(responseData);
            }

            br.close();

            try{
                user = mapper.readValue(sb.toString(), UserVO.class);
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }


}
