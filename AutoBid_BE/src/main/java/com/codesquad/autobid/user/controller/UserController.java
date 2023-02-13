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

//
//    @Operation(summary = "회원 조회 API", description = "회원 조회")
//    @GetMapping
//    public ResponseEntity<User> findById(@Parameter(hidden = true) @AuthorizedUser User user) {
//        return  new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @Operation(summary = "로그인 API", description = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<Optional<UserImpoResponse>> login(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getHeader("X-Auth-Code");
        log.info("code : {}",code);
        OauthToken oauthToken = authService.getOauthToken(code);
        User user = userService.findUser(oauthToken); // 유저 데이터 찾기
        UserImpoResponse userImpoResponse = UserImpoResponse.create(user.getId(), user.getName(), user.getEmail(), user.getMobilenum());
        Optional<UserImpoResponse> userResponse = Optional.of(userImpoResponse);

        if(userResponse.isPresent()){
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("user", userImpoResponse);
            httpSession.setAttribute(OauthToken.ACCESS_TOKEN_KEY, oauthToken.getAccessToken());
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<HttpStatus> delete(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String accessToken = (String) session.getAttribute(OauthToken.ACCESS_TOKEN_KEY);
        OauthToken deleteToken = authService.deleteOauthToken(accessToken);
        log.info("deleteToken : {}",deleteToken.getAccessToken());
        session.invalidate(); // 세션삭제
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
