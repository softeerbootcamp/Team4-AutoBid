package com.codesquad.autobid.user.controller;

import com.codesquad.autobid.auth.controller.AuthController;
import com.codesquad.autobid.auth.service.AuthService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.service.UserService;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(summary = "회원 조회 API", description = "회원 조회")
    @GetMapping
    public ResponseEntity<User> findById(@Parameter(hidden = true) @AuthorizedUser User user) {
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/session")
    public void login(String code, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
    }
}
