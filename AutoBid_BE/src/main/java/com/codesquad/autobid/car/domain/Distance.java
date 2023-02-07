package com.codesquad.autobid.car.domain;

import com.codesquad.autobid.handler.car.enums.DistanceUnit;
import com.codesquad.autobid.handler.car.vo.AvailableDistanceVO;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
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

    public static Distance from(AvailableDistanceVO availableDistanceVO) {
        return new Distance(availableDistanceVO.getDistance(), availableDistanceVO.getUnit());
    }

    @Override
    public String toString() {
        return distance + unit.getPlaceholder();
    }
}
