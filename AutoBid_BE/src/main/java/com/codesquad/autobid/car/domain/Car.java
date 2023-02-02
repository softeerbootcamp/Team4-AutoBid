package com.codesquad.autobid.car.domain;

import com.codesquad.autobid.handler.car.vo.CarVO;
import com.codesquad.autobid.user.User;

import java.time.LocalDateTime;

public class Car {

    private Long id;
    private User user;
    private State state;
    private Category category;
    private Long distance;
    // 현대차 API에서 사용하는 차량 식별용 아이디
    private String carId;
    private String carNickname;
    private String carType;
    private String carName;
    private String carSellname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Car() {
    }

    public static Car from(CarVO carVO) {
        Car car = new Car();
        car.carId = carVO.getCarId();
        car.carNickname = carVO.getCarNickname();
        car.carType = carVO.getCarType();
        car.carName = carVO.getCarName();
        car.carSellname = carVO.getCarSellname();
        return car;
    }
}
