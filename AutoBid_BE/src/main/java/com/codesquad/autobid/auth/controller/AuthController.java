package com.codesquad.autobid.auth.controller;


import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.handler.AuthHandler;
import com.codesquad.autobid.handler.OauthType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        logger.debug("accessToken : {}", oauthToken.getAccess_token());
    }
}
