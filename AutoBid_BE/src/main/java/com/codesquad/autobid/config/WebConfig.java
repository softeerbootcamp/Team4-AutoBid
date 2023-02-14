package com.codesquad.autobid.config;

import com.codesquad.autobid.web.argumentresolver.AccessTokenArgumentResolver;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUserArgumentResolver;
import com.codesquad.autobid.web.interceptor.AuthorizationInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AccessTokenArgumentResolver());
        resolvers.add(new AuthorizedUserArgumentResolver());
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthorizationInterceptor())
			.order(1)
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/index.html", "/user/login", "/swagger-ui/**","/swagger-ui.html","/v3/**","/test","/auction/list");
	}
}
