package com.codesquad.autobid.handler.car.vo;

public class CarVO {
    private String carId;
    private String carNickname;
    private String carType;
    private String carName;
    private String carSellname;

    private CarVO() {
    }

    public static CarVO from(String carId, String carNickname, String carType, String carName, String carSellname) {
        CarVO carVO = new CarVO();
        carVO.carId = carId;
        carVO.carNickname = carNickname;
        carVO.carType = carType;
        carVO.carName = carName;
        carVO.carSellname = carSellname;
        return carVO;
    }

    public String getCarId() {
        return carId;
    }

    public String getCarNickname() {
        return carNickname;
    }

    public String getCarType() {
        return carType;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarSellname() {
        return carSellname;
    }
}
