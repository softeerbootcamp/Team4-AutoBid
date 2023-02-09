package com.codesquad.autobid.handler.car.vo;

import com.codesquad.autobid.car.domain.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarVO {

    @JsonProperty("carId")
    private String carId;
    @JsonProperty("carNickname")
    private String nickname;
    @JsonProperty("carType")
    private Type type;
    @JsonProperty("carName")
    private String name;
    @JsonProperty("carSellname")
    private String sellName;
    @JsonIgnore
    private Long distance;

    public CarVO setDistance(DistanceVO distanceVO) {
        this.distance = distanceVO.getDistance();
        return this;
    }
}
