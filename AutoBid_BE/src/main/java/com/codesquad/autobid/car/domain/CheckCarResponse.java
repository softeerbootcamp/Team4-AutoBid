package com.codesquad.autobid.car.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckCarResponse {

    private Long id;
    private Float distance;
    private String name;
    private Type type;
    private String sellName;
    private State state;

    public static CheckCarResponse from(Car car) {
        CheckCarResponse response = new CheckCarResponse();
        response.id = car.getId();
        response.distance = car.getDistance();
        response.name = car.getName();
        response.type = car.getType();
        response.sellName = car.getSellName();
        response.state = car.getState();
        return response;
    }
}
