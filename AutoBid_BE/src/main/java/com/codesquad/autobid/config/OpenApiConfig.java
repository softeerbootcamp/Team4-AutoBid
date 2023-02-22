package com.codesquad.autobid.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

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

		Server server = new Server();
		server.setUrl("https://www.autobid.site");

		Server testServer = new Server();
		testServer.setUrl("http://localhost:8080");

		return new OpenAPI()
			.components(components)
			.servers(List.of(server, testServer))
			.info(info)
			.addSecurityItem(securityRequirement);
	}

}
