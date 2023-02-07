package com.codesquad.autobid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableJdbcAuditing
@SpringBootApplication
public class AutoBidApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoBidApplication.class, args);
	}

}
