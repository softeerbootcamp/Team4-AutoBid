package com.codesquad.autobid.handler.car.vo;

import com.codesquad.autobid.car.domain.Type;
import lombok.Getter;

@Getter
public class CarVO {

    private String carId;
    private Type type;
    private String name;
    private String sellName;
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
}
