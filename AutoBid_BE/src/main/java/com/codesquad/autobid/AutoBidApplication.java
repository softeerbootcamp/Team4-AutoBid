package com.codesquad.autobid;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableRedisHttpSession
@EnableJdbcAuditing
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableSchedulerLock(defaultLockAtLeastFor = "PT10S", defaultLockAtMostFor = "PT5M")
public class AutoBidApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoBidApplication.class, args);
    }

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
// 				registry.addMapping("/**")
// 					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
// 					.allowedHeaders("Access-Control-Allow-Origin","Authorization", "Refreshtoken","Access-Control-Request-Method", "origin", "x-requested-with","Content-Type","Access-Control-Allow-Headers","Access-Control-Request-Headers")
// 					.allowedOrigins("http://localhost:1234", "http://localhost:51791", "http://localhost:3000", "https://www.autobid.im", "http://localhost:8080","https://www.autobid.site")
// 					.allowCredentials(true)
// 					.maxAge(3000);
			}

		};
	}
}
