package com.codesquad.autobid.user.controller;

import com.codesquad.autobid.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getSession().getAttribute("user");

        ResponseEntity<User> userEntity = new ResponseEntity<>(user, HttpStatus.OK);

        return userEntity;
    }

    /**
     * TODO
     *  1. UserResponse 만든다.
     *  2. 그걸 HttpEntity 에 쏴준다.
     * **/
}
