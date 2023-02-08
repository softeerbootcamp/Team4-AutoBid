package com.codesquad.autobid.car.service;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.CheckCarResponse;
import com.codesquad.autobid.car.repository.CarRepository;
import com.codesquad.autobid.handler.car.CarHandler;
import com.codesquad.autobid.handler.car.vo.CarVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    private final CarHandler carHandler;

    public CarService(CarRepository carRepository, CarHandler carHandler) {
        this.carRepository = carRepository;
        this.carHandler = carHandler;
    }

    @Transactional
    public List<CheckCarResponse> getCars(Long userId, String accessToken, boolean refresh) {
        List<Car> cars = carRepository.findCarsByUserId(userId);
        if (refresh) {
            List<Car> updatedCars = getUpdatedCars(cars, accessToken, userId);
            List<Car> oldCars = retainOldCars(cars, updatedCars);
            carRepository.deleteAll(oldCars);
            carRepository.saveAll(updatedCars);
            cars = updatedCars;
        }
        return cars.stream().map(CheckCarResponse::from).collect(Collectors.toList());
    }

    private List<Car> getUpdatedCars(List<Car> cars, String accessToken, Long userId) {
        return carHandler.getCars(accessToken).stream()
                .map(carVO -> carVO.addDistanceInfo(carHandler.getAvailableDistance(accessToken, carVO.getCarId())))
                .map(carVO -> updated(carVO, findByCarId(cars, carVO.getCarId()), userId))
                .collect(Collectors.toList());
    }

    private List<Car> retainOldCars(List<Car> cars, List<Car> updatedCars) {
        for (Car car : cars) {
            for (Car updatedCar : updatedCars) {
                if (car.getCarId().equals(updatedCar.getCarId())) {
                    cars.remove(car);
                }
            }
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
