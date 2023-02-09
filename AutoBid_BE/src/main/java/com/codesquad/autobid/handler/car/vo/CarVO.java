package com.codesquad.autobid.handler.car.vo;

import com.codesquad.autobid.car.domain.Type;
import com.codesquad.autobid.handler.car.enums.DistanceUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CarVO {

    @JsonProperty("carId")
    private String carId;
    @JsonProperty("carType")
    private Type type;
    @JsonProperty("carName")
    private String name;
    @JsonProperty("carSellname")
    private String sellName;
    @JsonProperty("carNickname")
    private String nickname;
    @JsonIgnore
    private String distance;

    public CarVO setDistanceVO(DistanceVO distanceVO) {
        this.distance = distanceVO.getDistance() + DistanceUnit.findByCode(distanceVO.getUnitCode()).getPlaceholder();
        return this;
    }
}
