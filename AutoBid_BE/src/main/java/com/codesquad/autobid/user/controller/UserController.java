package com.codesquad.autobid.user.controller;

import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Operation(summary = "회원 조회 API", description = "회원 조회")
    @GetMapping
    public ResponseEntity<User> findById(@Parameter(hidden = true) @AuthorizedUser User user) {
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }
}
