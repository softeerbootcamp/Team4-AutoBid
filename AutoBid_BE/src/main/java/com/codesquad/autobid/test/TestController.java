package com.codesquad.autobid.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/test")
	public String GetTest(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		httpSession.setAttribute("test", "test");

		return "OK";
	}
	
	@PostMapping("/test")
	public String postTest(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		httpSession.setAttribute("test", "test");

		return "OK";
	}
	
	@GetMapping("/session/test")
	public String getSessionTest(HttpServlet Request httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		return httpSession.getAttribute("test");
	}
}
