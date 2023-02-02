package com.codesquad.autobid.car;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.handler.car.CarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    private final CarHandler carHandler;
    private final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    public CarController(CarHandler carHandler) {
        this.carHandler = carHandler;
    }

    @GetMapping("/user/{id}/cars")
    public List<Car> getCars(@PathVariable("id") Long id, String accessToken) {
        logger.debug("id={}, accessToken={}", id, accessToken);
        return carHandler.getCars(accessToken);
    }
}
