package com.codesquad.autobid.car.service;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.repository.CarRepository;
import com.codesquad.autobid.handler.car.CarHandler;
import com.codesquad.autobid.handler.car.vo.CarVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarHandler carHandler;

    public CarService(CarRepository carRepository, CarHandler carHandler) {
        this.carRepository = carRepository;
        this.carHandler = carHandler;
    }

    public List<Car> getCars(Long userId, String accessToken, boolean refresh) {
        List<Car> cars = carRepository.findCarsByUserId(userId);
        if (refresh) {
            List<Car> updatedCars = carHandler.getCars(accessToken).stream()
                    .map(carVO -> carVO.addDistanceInfo(carHandler.getAvailableDistance(accessToken, carVO.getCarId())))
                    .map(carVO -> updated(carVO, findByCarId(cars, carVO.getCarId()), userId))
                    .collect(Collectors.toList());
            carRepository.saveAll(updatedCars);
            return updatedCars;
        }
        return cars;
    }

    private Car updated(CarVO carVO, Car car, Long userId) {
        if (car != null) {
            car.update(carVO);
            return car;
        }
        return Car.from(carVO, userId);
    }

    private Car findByCarId(List<Car> cars, String carId) {
        return cars.stream()
                .filter(car -> car.getCarId().equals(carId))
                .findAny()
                .orElse(null);
    }
}
