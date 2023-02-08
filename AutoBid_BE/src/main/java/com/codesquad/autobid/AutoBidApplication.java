package com.codesquad.autobid;

import java.util.HashSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableRedisHttpSession
@EnableJdbcAuditing
@SpringBootApplication
public class AutoBidApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoBidApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("Access-Control-Allow-Origin","Authorization", "Refreshtoken","Access-Control-Request-Method", "origin", "x-requested-with","Content-Type","Access-Control-Allow-Headers","Access-Control-Request-Headers")
					.allowedOrigins("http://localhost:1234","http://localhost:3000", "https://autobid.im")
					.allowCredentials(true)
					.maxAge(3000);
			}

		};
	}
}
