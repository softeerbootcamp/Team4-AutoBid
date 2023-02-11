package com.codesquad.autobid.car.repository;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.util.CarTestUtil;
import com.codesquad.autobid.user.domain.User;
import com.codesquad.autobid.user.repository.UserRepository;
import com.codesquad.autobid.user.util.UserTestUtil;
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
class CarRepositoryTest {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Autowired
    public CarRepositoryTest(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("자동차 추가 성공")
    void save_car() {
        // given
        User user = UserTestUtil.getNewUser();
        user = userRepository.save(user);
        Car car = CarTestUtil.getNewCars(user.getId(), 1).get(0);
        // when
        Car scar = carRepository.save(car);
        // then
        assertThat(car).isEqualTo(scar);
    }

    @Test
    @DisplayName("유저 아이디로 차량 조회 성공")
    void findCarByUserId() {
        // given
        User user = UserTestUtil.getNewUser();
        user = userRepository.save(user);
        Car car = CarTestUtil.getNewCars(user.getId(), 1).get(0);
        carRepository.save(car);
        // when
        List<Car> cars = carRepository.findCarsByUserId(user.getId());
        // then
        assertAll(
                () -> assertThat(cars.size()).isEqualTo(1),
                () -> assertThat(car.getCarId()).isEqualTo(cars.get(0).getCarId())
        );
    }
}
