package com.codesquad.autobid.config;

import com.codesquad.autobid.web.argumentresolver.AccessTokenArgumentResolver;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUserArgumentResolver;
import com.codesquad.autobid.web.interceptor.AuthorizationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Objects;

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
                .excludePathPatterns("/", "/index.html", "/user/login", "/auction-room/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/**", "/test", "/auction/list", "/auction/statistics", "/ws/**", "/ws");
    }
}
