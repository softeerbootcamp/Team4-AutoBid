package com.codesquad.autobid.auth.service;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.handler.AuthHandler;
import com.codesquad.autobid.handler.OauthType;
import com.codesquad.autobid.handler.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthHandler authHandler;
    private final UserHandler userHandler;

    @Autowired
    public AuthService(AuthHandler authHandler, UserHandler userHandler) {
        this.authHandler = authHandler;
        this.userHandler = userHandler;
    }

    public OauthToken getOauthToken(String code) {
        return authHandler.getOauthToken(OauthType.NEW, code);
    }

//    public OauthToken deleteOauthToken(String accessToken) {
//        return authHandler.getOauthDeleteToken(OauthType.DELETE, accessToken);
//    }
//
//    public OauthToken refreshOauthToken(String refreshToken) {
//        return authHandler.getOauthRefreshToken(OauthType.REFRESH, refreshToken);
//    }
}
