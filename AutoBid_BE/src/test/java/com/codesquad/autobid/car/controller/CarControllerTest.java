package com.codesquad.autobid.car.controller;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.repository.CarRepository;
import com.codesquad.autobid.car.util.CarTestUtil;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class CarControllerTest {

    private final CarController carController;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private static User user;

    @Autowired
    public CarControllerTest(CarController carController, UserRepository userRepository, CarRepository carRepository) {
        this.carController = carController;
        this.carRepository = carRepository;
        this.userRepository = userRepository;

        user = CarTestUtil.getNewUser();
        userRepository.save(user);
    }

    @BeforeEach
    void beforeEach() {
        List<Car> cars = CarTestUtil.getNewCars(user.getId(), 3);
        carRepository.saveAll(cars);
    }

    @Test
    @DisplayName("기존 차량 목록 조회 성공")
    void getCarsSuccess() {
        // given
        String accessToken = "accessToken";
        boolean refresh = false;
        // when
        List<Car> cars = carController.getCars(user, accessToken, refresh);
        // then
        Assertions.assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(cars.size()).isEqualTo(3)
        );
    }
}