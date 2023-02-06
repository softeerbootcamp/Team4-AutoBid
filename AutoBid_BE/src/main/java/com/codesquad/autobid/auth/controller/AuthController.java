package com.codesquad.autobid.auth.controller;


import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.auth.service.AuthService;
import com.codesquad.autobid.handler.OauthType;
import com.codesquad.autobid.user.domain.Users;
import com.codesquad.autobid.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    private final AuthService authService;

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/oauth/login")
    public void oauth(String code, HttpServletRequest httpServletRequest) {
        OauthToken oauthToken = authService.getOauthToken(code);

        String accessToken = oauthToken.getAccess_token();

        Users user = userService.findUser(oauthToken);

        HttpSession httpSession = httpServletRequest.getSession();

        httpSession.setAttribute("user",user);
        httpSession.setAttribute("access_token",accessToken);








        String refreshtoken = oauthToken.getRefresh_token();
    }
}
