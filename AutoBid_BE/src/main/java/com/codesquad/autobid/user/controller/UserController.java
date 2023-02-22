package com.codesquad.autobid.user.controller;

import com.codesquad.autobid.OauthToken;
import com.codesquad.autobid.auth.service.AuthService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.domain.UserImpoResponse;
import com.codesquad.autobid.user.service.UserService;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(summary = "로그인 API", description = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<UserImpoResponse> login(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getHeader("X-Auth-Code");
        OauthToken oauthToken = authService.getOauthToken(code);
        User user = userService.findUser(oauthToken); // 유저 데이터 찾기
        UserImpoResponse userImpoResponse = UserImpoResponse.create(user.getId(), user.getName(), user.getEmail(), user.getMobilenum());
        Optional<UserImpoResponse> userResponse = Optional.of(userImpoResponse);

        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("user", user);
        httpSession.setAttribute(OauthToken.ACCESS_TOKEN_KEY, oauthToken.getAccessToken());
        return new ResponseEntity<>(userResponse.get(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> delete(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.invalidate(); // 세션삭제
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<UserImpoResponse> userResponse(@AuthorizedUser User user) {
        return ResponseEntity.ok()
            .body(UserImpoResponse.create(user.getId(), user.getName(), user.getEmail(), user.getMobilenum()));
    }
}
