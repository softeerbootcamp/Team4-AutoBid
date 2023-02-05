package com.codesquad.autobid.car.repository;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.Distance;
import com.codesquad.autobid.car.domain.State;
import com.codesquad.autobid.car.domain.Type;
import com.codesquad.autobid.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        Car car = getNewCar(24l);
        // when
        Car scar = carRepository.save(car);
        // then
        assertThat(car).isEqualTo(scar);
    }

    @Test
    @DisplayName("유저 아이디로 차량 조회 성공")
    void findCarByUserId() {
        // given
        Car car = getNewCar(24l);
        carRepository.save(car);
        // when
        List<Car> cars = carRepository.findCarsByUserId(24l);
        // then
        assertAll(
                () -> assertThat(cars.size()).isEqualTo(1),
                () -> assertThat(car.getCarId()).isEqualTo(cars.get(0).getCarId())
        );
    }

    private Car getNewCar(Long userId) {
        return new Car(null, userId, State.NOT_FOR_SALE, Type.ETC, Distance.from("0 KM"), "id#1", "name#1", "sellname#1", LocalDateTime.now(), LocalDateTime.now());
    }
}
