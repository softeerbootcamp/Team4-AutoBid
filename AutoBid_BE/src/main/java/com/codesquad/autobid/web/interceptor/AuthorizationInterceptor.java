package com.codesquad.autobid.web.interceptor;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.test.UserTestUtil;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (request.getHeader("Authorization") != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", UserTestUtil.TEST_USER_1);
			session.setAttribute("accessToken", "accessToken");
		}

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(OauthToken.ACCESS_TOKEN_KEY) == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		return true;
	}
}
