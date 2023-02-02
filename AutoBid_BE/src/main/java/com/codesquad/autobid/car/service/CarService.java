package com.codesquad.autobid.car.service;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars(Long id) {
        return carRepository.findCarsByUserId(id);
    }
}
