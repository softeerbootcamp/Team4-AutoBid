package com.codesquad.autobid.handler.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class UrlReader {

    public static String reader(URL url, String access_token) throws IOException {
        String responseData = "";
        StringBuffer sb;
        BufferedReader br;

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // Set Header Info
        con.setRequestProperty("Authorization", "Bearer " + access_token);
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
        return sb.toString();

    }
}
