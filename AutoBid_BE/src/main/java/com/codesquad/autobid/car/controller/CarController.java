package com.codesquad.autobid.car.controller;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.service.CarService;
import com.codesquad.autobid.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    // todo: filter(interceptor)에서 session 받는걸로 변경하기
    // @SessionAttribute(name = "user") User user, @SessionAttribute(name = "accessToken") String accessToken, boolean refresh
    public List<Car> getCars(@PathVariable("id") Long id, boolean refresh, String accessToken) {
        logger.info("user/{}/cars refresh={} accessToken={}", id,refresh, accessToken);
        return carService.getCars(id, accessToken, refresh);
    }
}
