package com.codesquad.autobid.auth.controller;


import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.handler.AuthHandler;
import com.codesquad.autobid.handler.OauthType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthHandler authHandler;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @GetMapping("/oauth/login")
    public void oauth(String code) {
        OauthToken oauthToken = authHandler.getOauthToken(OauthType.NEW, code);

        String accessToken = oauthToken.getAccess_token();
        String refreshtoken = oauthToken.getRefresh_token();


        authHandler.userProfileAPICall(accessToken);

        Map<String, String> map = new HashMap<>();
    }

    @RequestMapping(value = "/authorization") //설정한 redirect_uri에 맞게 정의
    public void account(@RequestParam(value="code") String code,
                        @RequestParam(value="state") String state,
                        HttpServletResponse response) throws IOException {

        String requestState = "{YOUR_STATE_TEXT}"; //request로 설정한 state와 동일한 값
        String redirectURL = "{YOUR_RECIRECT_URI}";


        // SUCCESS 200 Response code, state
        System.out.println("RESPONSE_STATE = " + state);
        System.out.println("RESPONSE_CODE = " + code);


        // state 검증
        if(!state.equals(requestState)) {
            System.out.println(state + " 유효하지 않은 state 응답입니다.");
            return;
        }
    }
}
