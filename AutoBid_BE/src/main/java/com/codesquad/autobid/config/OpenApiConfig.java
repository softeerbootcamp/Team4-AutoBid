package com.codesquad.autobid.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI(@Value("1.6.12") String springdocVersion) {

		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("Bearer")
			.bearerFormat("JWT");

		Components components = new Components()
			.addSecuritySchemes("bearer-key", securityScheme);

		Info info = new Info()
			.title("HYUNDAI AutoBid API DOCS")
			.version(springdocVersion)
			.description("HYUNDAI AutoBid API DOCS");

		SecurityRequirement securityRequirement = new SecurityRequirement();
		securityRequirement.addList("bearer-key");

		return new OpenAPI()
			.components(components)
			.info(info)
			.addSecurityItem(securityRequirement);
	}
}
