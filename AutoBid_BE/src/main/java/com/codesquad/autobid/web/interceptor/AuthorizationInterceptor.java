package com.codesquad.autobid.web.interceptor;

import java.util.Enumeration;
import java.util.Objects;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.test.UserTestUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (Objects.equals(request.getHeader("Authorization"), "Bearer random")) {
			HttpSession session = request.getSession();
			session.setAttribute("user", UserTestUtil.RANDOM_USER);
			session.setAttribute("accessToken", "accessToken");
		}

		HttpSession session = request.getSession(false);
		log.info("session : {}",session);

		if (session == null || session.getAttribute(OauthToken.ACCESS_TOKEN_KEY) == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		return true;
	}
}
