package com.codesquad.autobid.car.domain;

import com.codesquad.autobid.handler.car.enums.DistanceUnit;
import com.codesquad.autobid.handler.car.vo.DistanceVO;
import lombok.Getter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

@Getter
public class Distance {

    @Column("car_distance")
    private Long distance;
    @Column("car_distance_unit")
    private DistanceUnit unit;
    @Transient
    private int unitCode;

    public Distance(Long distance, int unitCode) {
        this.distance = distance;
        this.unitCode = unitCode;
        this.unit = DistanceUnit.findByCode(unitCode);
    }

    @Override
    public String toString() {
        return distance + unit.getPlaceholder();
    }
}
