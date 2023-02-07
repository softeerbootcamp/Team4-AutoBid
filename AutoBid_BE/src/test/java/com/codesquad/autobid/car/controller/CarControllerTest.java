package com.codesquad.autobid.car.controller;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.CheckCarResponse;
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
import org.springframework.http.ResponseEntity;
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
    public CarControllerTest(CarController carController, CarRepository carRepository, UserRepository userRepository) {
        this.carController = carController;
        this.carRepository = carRepository;
        this.userRepository = userRepository;

        user = CarTestUtil.getNewUser();
    }

    @BeforeEach
    void beforeEach() {
        userRepository.save(user);
        List<Car> cars = CarTestUtil.getNewCars(user.getId(), 3);
        carRepository.saveAll(cars);
    }

    @Test
    @DisplayName("기존 차량 목록 조회 성공")
    void getCarsSuccess() {
        // given
        String accessToken = "accessToken";
        // when
        ResponseEntity<List<CheckCarResponse>> response = carController.getCars(user, accessToken, false);
        // then
        List<CheckCarResponse> body = response.getBody();
        Assertions.assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(body.size()).isEqualTo(3)
        );
    }
}