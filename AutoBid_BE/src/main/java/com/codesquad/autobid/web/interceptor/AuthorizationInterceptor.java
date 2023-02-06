package com.codesquad.autobid.web.interceptor;

import com.codesquad.autobid.OauthToken;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        if (session.getAttribute(OauthToken.ACCESS_TOKEN_KEY) == null) {
            return false;
        }
        return true;
    }
}
