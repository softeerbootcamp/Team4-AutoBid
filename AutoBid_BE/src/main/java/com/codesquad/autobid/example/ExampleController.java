package com.codesquad.autobid.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class ExampleController {

	private final Logger logger = LoggerFactory.getLogger(ExampleController.class);
	@Operation(summary = "경매 조회 API", description = "경매 정보를 조회합니다.")
	@PostMapping("/test")
	public void test(@Parameter(description = "경매 고유 ID") @RequestParam String id) {
		logger.info("id = {}", id);
	}
}
