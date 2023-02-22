package com.codesquad.autobid.handler.car.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class CarListVO {

    @JsonProperty("cars")
    private List<CarVO> cars = new ArrayList<>();

    @JsonProperty("msgId")
    private String message = null;
}
