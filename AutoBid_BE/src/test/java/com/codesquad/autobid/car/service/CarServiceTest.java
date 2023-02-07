package com.codesquad.autobid.car.service;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.repository.CarRepository;
import com.codesquad.autobid.car.util.CarTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class CarServiceTest {

    private final CarService carService;
    private final CarRepository carRepository;

    @Autowired
    public CarServiceTest(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    @DisplayName("갱신이 아닌 내 차 목록 조회 기능")
    void 갱신없는_요청_성공() {
        // given
        Long userId = 24l;
        String accessToken = "accessToken#1";
        boolean refresh = false;
        saveCar(userId, 3);
        // when
        List<Car> cars = carService.getCars(userId, accessToken, refresh);
        // then
        assertAll(
                () -> assertThat(cars.size()).isEqualTo(3)
        );
    }

    private void saveCar(Long userId, int count) {
        List<Car> cars = CarTestUtil.getNewCars(userId, count);
        for (Car car : cars) {
            carRepository.save(car);
        }
    }
}