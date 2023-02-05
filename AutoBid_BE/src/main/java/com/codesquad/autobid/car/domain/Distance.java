package com.codesquad.autobid.car.domain;

import com.codesquad.autobid.handler.car.enums.DistanceUnit;
import org.springframework.data.relational.core.mapping.Column;

public class Distance {

    @Column("car_distance")
    private Long distance;
    @Column("car_distance_unit")
    private DistanceUnit unit;

    private Distance(Long distance, DistanceUnit unit) {
        this.distance = distance;
        this.unit = unit;
    }

    public static Distance from(String carDistance) {
        String[] chunks = carDistance.split("\\s");
        return new Distance(Long.parseLong(chunks[0]), DistanceUnit.valueOf(chunks[1]));
    }

    @Override
    public String toString() {
        return distance + " " + unit.getPlaceholder();
    }
}
