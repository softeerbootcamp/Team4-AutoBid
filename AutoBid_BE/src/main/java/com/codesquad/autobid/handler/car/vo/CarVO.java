package com.codesquad.autobid.handler.car.vo;

import com.codesquad.autobid.car.domain.Type;

public class CarVO {

    private String carId;
    private Type type;
    private String name;
    private String sellname;
    private AvailableDistanceVO availableDistanceVO;

    private CarVO() {
    }

    public AvailableDistanceVO getAvailableDistanceVO() {
        return availableDistanceVO;
    }

    public CarVO addDistanceInfo(AvailableDistanceVO availableDistanceVO) {
        this.availableDistanceVO = availableDistanceVO;
        return this;
    }

    public String getCarId() {
        return carId;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSellname() {
        return sellname;
    }
}
