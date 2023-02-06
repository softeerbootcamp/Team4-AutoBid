package com.codesquad.autobid.car.controller;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.service.CarService;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.web.argumentresolver.AccessToken;
import com.codesquad.autobid.web.argumentresolver.AuthorizedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/user/{id}/cars")
    public List<Car> getCars(@AuthorizedUser User user, @AccessToken String accessToken, boolean refresh) {
        logger.info("user/{}/cars refresh={} accessToken={}", user.getId(), refresh, accessToken);
        return carService.getCars(user.getId(), accessToken, refresh);
    }
}
