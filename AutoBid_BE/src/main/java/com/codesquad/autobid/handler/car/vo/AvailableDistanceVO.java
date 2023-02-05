package com.codesquad.autobid.handler.car.vo;

import com.codesquad.autobid.handler.car.enums.DistanceUnit;

public class AvailableDistanceVO {

    private Long distance;
    private DistanceUnit unit;

    public AvailableDistanceVO(Long distance, int code) {
        this.distance = distance;
        this.unit = DistanceUnit.findByCode(code);
    }

    public Long getDistance() {
        return distance;
    }

    public DistanceUnit getUnit() {
        return unit;
    }
}
