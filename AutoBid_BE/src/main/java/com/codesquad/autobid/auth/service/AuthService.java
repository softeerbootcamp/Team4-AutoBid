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

    @Autowired
    public AuthService(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    public OauthToken getOauthToken(String code) {
        return authHandler.getOauthToken(OauthType.NEW, code);
    }
}
