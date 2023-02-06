package com.codesquad.autobid.handler;

import com.codesquad.autobid.user.domain.UserVO;
import com.codesquad.autobid.user.domain.Users;
import com.codesquad.autobid.user.repository.UserRepository;
import com.codesquad.autobid.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.codesquad.autobid.OauthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthHandler {

	private Logger logger = LoggerFactory.getLogger(AuthHandler.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final RestTemplate rt = new RestTemplate();

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Value("${hyundai.auth.redirect_uri}")
	private String REDIRECT_URI;

	@Value("${hyundai.auth.authorization_key}")
	private String AUTHORIZATION_KEY;

	@Value("${hyundai.auth.token_request_uri}")
	private String TOKEN_REQUEST_URI;

	// JSON 응답을 객체로 변환
	private OauthToken oauthToken = null;

	public OauthToken getOauthToken(OauthType oauthType, String value) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", oauthType.getGrantType()); // 고정값
		params.add("redirect_uri", REDIRECT_URI);
		params.add(oauthType.getTokenType(), value);

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

	public void userProfileAPICall(String accessToken) {    // 발급받은 Access Token
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
			con.setRequestProperty("Authorization", "Bearer " + accessToken);

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

			userService.login(user, oauthToken);
			logger.info("responseCode = {}",responseCode);
			logger.info("userData = {}",sb.toString());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
