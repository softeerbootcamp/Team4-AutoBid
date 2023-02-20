package com.codesquad.autobid.config;

import com.codesquad.autobid.web.argumentresolver.AccessTokenArgumentResolver;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUserArgumentResolver;
import com.codesquad.autobid.web.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedHeader("*");
                corsConfiguration.addAllowedMethod("*");
                corsConfiguration.addAllowedOrigin("*");
                return corsConfiguration;
            }
        };
    }

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
