package com.codesquad.autobid.car.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckCarResponse {

    private String distance;
    private String name;
    private Type type;
    private String sellName;
    private State state;

    public static CheckCarResponse from(Car car) {
        CheckCarResponse response = new CheckCarResponse();
        response.distance = car.getDistance().toString();
        response.name = car.getName();
        response.type = car.getType();
        response.sellName = car.getSellName();
        response.state = car.getState();
        return response;
    }
}
