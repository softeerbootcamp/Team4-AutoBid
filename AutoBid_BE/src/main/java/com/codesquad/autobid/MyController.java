package com.codesquad.autobid;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

	@GetMapping("/oauth/test")
	public void ouath(HttpServletRequest request, HttpServletRequest) {

	}
}
