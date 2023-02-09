package com.codesquad.autobid.car.controller;

import com.codesquad.autobid.car.domain.CheckCarResponse;
import com.codesquad.autobid.car.service.CarService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AccessToken;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/user-cars")
    public ResponseEntity<List<CheckCarResponse>> getCars(
            @Parameter(hidden = true) @AuthorizedUser User user,
            @Parameter(hidden = true) @AccessToken String accessToken,
            @RequestParam(name = "refresh", required = false, defaultValue = "true") boolean refresh) {
        log.info("user/{}/cars refresh={} accessToken={}", user.getId(), refresh, accessToken);
        List<CheckCarResponse> cars = carService.getCars(user.getId(), accessToken, refresh);

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
}
