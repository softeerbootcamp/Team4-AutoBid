package com.codesquad.autobid.user.controller;

import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ResponseEntity<User> findById(@AuthorizedUser User user) {
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * TODO
     *  1. UserResponse 만든다.
     *  2. 그걸 HttpEntity 에 쏴준다.
     * **/
}
