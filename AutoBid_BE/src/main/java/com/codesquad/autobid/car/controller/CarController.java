package com.codesquad.autobid.car.controller;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.service.CarService;
import com.codesquad.autobid.handler.car.CarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarHandler carHandler;
    private final CarService carService;

    @Autowired
    public CarController(CarHandler carHandler, CarService carService) {
        this.carHandler = carHandler;
        this.carService = carService;
    }

    @GetMapping("/user/{id}/cars")
    public List<Car> getCars(@PathVariable("id") Long id, @RequestParam(name = "refresh", required = false) boolean refresh, String accessToken) {
        logger.info("id={}, accessToken={}", id, accessToken);
        if (refresh) {
            return carHandler.getCars(accessToken);
        }
        return carService.getCars(id);
    }
}
