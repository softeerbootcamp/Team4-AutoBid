package com.codesquad.autobid.auth.controller;


import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.auth.service.AuthService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    /**
     *  TODO
     *   1. SameSite 허용 : 쿠키 허용
     *   2. 클라이언트에 user 정보 전달
     * **/
//
//    private final AuthService authService;
//    private final UserService userService;
//    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
//
//    @Autowired
//    public AuthController(AuthService authService, UserService userService) {
//        this.authService = authService;
//        this.userService = userService;
//    }
//
//    @Operation(summary = "로그인 API", description = "로그인을 합니다.")
//    @GetMapping("/oauth/login")
//    public void oauth(String code, HttpServletRequest httpServletRequest) {
//        OauthToken oauthToken = authService.getOauthToken(code);
//        User user = userService.findUser(oauthToken);
//
//        HttpSession httpSession = httpServletRequest.getSession();
//        httpSession.setAttribute("user", user);
//        httpSession.setAttribute("accessToken", oauthToken.getAccessToken());
//    }
}
