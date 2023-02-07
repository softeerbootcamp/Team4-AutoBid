package com.codesquad.autobid.car.service;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.CheckCarResponse;
import com.codesquad.autobid.car.repository.CarRepository;
import com.codesquad.autobid.car.util.CarTestUtil;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public CarServiceTest(CarService carService, CarRepository carRepository, UserRepository userRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    @DisplayName("갱신이 아닌 내 차 목록 조회 기능")
    void 갱신없는_요청_성공() {
        // given
        User user = CarTestUtil.getNewUser();
        user = userRepository.save(user);
        String accessToken = "accessToken#1";
        boolean refresh = false;
        saveCar(user.getId(), 3);
        // when
        List<CheckCarResponse> cars = carService.getCars(user.getId(), accessToken, refresh);
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